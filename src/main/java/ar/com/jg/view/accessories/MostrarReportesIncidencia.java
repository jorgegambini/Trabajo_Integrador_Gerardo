package ar.com.jg.view.accessories;

import ar.com.jg.model.Tecnico;
import ar.com.jg.services.ReporteIncidenciaService;
import ar.com.jg.services.ReporteIncidenciaServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;


public class MostrarReportesIncidencia extends JPanel {

    private ReporteIncidenciaService rs;

    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel modelo;
    private Tecnico tecnico;

    public MostrarReportesIncidencia(EntityManager em, Tecnico tecnico) {

        this.tecnico = tecnico;

        rs = new ReporteIncidenciaServiceImpl(em);

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        table = new JTable();

        scroll = new JScrollPane(table);
        add(scroll, "width 1350:1350:1350, height 150:150:150");

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

        String[] columnNames = {"F. ALTA", "CODIGO", "CLIENTE", "OPERADOR", "SERVICIO", "TIPO PROBLEMA", "TECNICO", "T. RESOLUCION", "F. RESOLUCION", "ESTADO"};
        modelo.setColumnIdentifiers(columnNames);

        // Define el ancho de las columnas
        int[] columnWidths = {90, 100, 220, 200, 150, 120, 200, 100, 100, 70}; // Ancho en píxeles para cada columna
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
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);

        cargarTabla();

    }

    private void cargarTabla() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        modelo.setRowCount(0);

        if (tecnico == null) {

            rs.listarReportesIncidencia().forEach(report -> {

                modelo.addRow(new Object[]{report.getFechaAlta().format(formatter), report.getCodigoReporte(), report.getCliente(), report.getOperador(), report.getServicio(), report.getTipoProblema().name(), report.getTecnico(), report.getTiempoEstimadoResolucion(), report.getFechaPosibleResolucion().format(formatter), report.getEstadoProblema().name()});

            });

        } else {

            rs.listarReportesIncidencia().stream().filter(rep -> rep.getTecnico().equals(tecnico)).forEach(report -> {

                modelo.addRow(new Object[]{report.getFechaAlta().format(formatter), report.getCodigoReporte(), report.getCliente(), report.getOperador(), report.getServicio(), report.getTipoProblema().name(), report.getTecnico(), report.getTiempoEstimadoResolucion(), report.getFechaPosibleResolucion().format(formatter), report.getEstadoProblema().name()});

            });

        }

        if (table.getRowCount() > 0) {

            table.setRowSelectionInterval(0, 0);

        }

    }

}