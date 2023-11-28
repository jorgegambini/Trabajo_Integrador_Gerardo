package ar.com.jg.view.accessories;

import ar.com.jg.model.Especialidad;
import ar.com.jg.model.ReporteIncidencia;
import ar.com.jg.model.Tecnico;
import ar.com.jg.services.EspecialidadService;
import ar.com.jg.services.EspecialidadServiceImpl;
import ar.com.jg.services.ReporteIncidenciaService;
import ar.com.jg.services.ReporteIncidenciaServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MostrarReportes extends JPanel {

    private ReporteIncidenciaService rs;
    private EspecialidadService es;

    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel modelo;

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private int optReportes;
    private Especialidad especialidad;

    private String[] columnNames;

    public MostrarReportes(EntityManager em, int optReportes, LocalDate fechaDesde, LocalDate fechaHasta, Especialidad especialidad) {

        rs = new ReporteIncidenciaServiceImpl(em);
        es = new EspecialidadServiceImpl(em);

        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;

        this.optReportes = optReportes;

        this.especialidad = especialidad;

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        table = new JTable();

        scroll = new JScrollPane(table);
        add(scroll, "width 450:450:450, height 150:150:150");

        dibujarTabla();

        table.addAncestorListener(new RequestFocusListener());

    }

    private void dibujarTabla() {

        modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;

            }

        };

        table.setModel(modelo);

        if (optReportes == 3) {

            columnNames = new String[]{"TECNICO", "PROM. RESOL."};


        }else {

            columnNames = new String[]{"TECNICO", "Nº RESUELTOS"};

        }
        modelo.setColumnIdentifiers(columnNames);

        // Define el ancho de las columnas
        int[] columnWidths = {300, 150}; // Ancho en píxeles para cada columna
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Personalizar la alineación y la fuente de la cabecera
        table.getTableHeader().setReorderingAllowed(false);

        // Crear renderizadores personalizados para diferentes alineaciones
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        cargarTabla();

    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        List<ReporteIncidencia> listaReportesPorRango = rs.listarReportesIncidenciaPorRangoFecha(fechaDesde, fechaHasta);

        if (optReportes == 1) {

            TreeMap<Tecnico, Long> cantidadReportesResultosxTecnico = listaReportesPorRango.stream().collect(Collectors.groupingBy(ReporteIncidencia::getTecnico, TreeMap::new, Collectors.counting()));

            cantidadReportesResultosxTecnico.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(map -> {

                modelo.addRow(new Object[]{map.getKey(), map.getValue()});

            });

        } else if (optReportes == 2) {

            TreeMap<Tecnico, Long> cantidadReportesResultosxTecnico = listaReportesPorRango.stream().filter(repor -> repor.getTecnico().getEspecialidades().contains(especialidad)).collect(Collectors.groupingBy(ReporteIncidencia::getTecnico, TreeMap::new, Collectors.counting()));

            cantidadReportesResultosxTecnico.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(map -> {

                modelo.addRow(new Object[]{map.getKey(), map.getValue()});

            });

        } else {

            TreeMap<Tecnico, Double> cantidadReportesResultosxTecnico = listaReportesPorRango.stream().collect(Collectors.groupingBy(ReporteIncidencia::getTecnico, TreeMap::new, Collectors.averagingInt(ReporteIncidencia::getTiempoEstimadoResolucion)));

            cantidadReportesResultosxTecnico.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).forEachOrdered(map -> {

                modelo.addRow(new Object[]{map.getKey(), map.getValue()});

            });

        }

        if (table.getRowCount() > 0) {

            table.setRowSelectionInterval(0, 0);

        }

    }

}