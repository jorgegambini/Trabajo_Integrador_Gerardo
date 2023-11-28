package ar.com.jg.view.accessories;

import ar.com.jg.services.ServicioService;
import ar.com.jg.services.ServicioServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class MostrarServicios extends JPanel {

    private ServicioService ss;

    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel modelo;

    public MostrarServicios(EntityManager em) {

        ss = new ServicioServiceImpl(em);

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

        String[] columnNames = {"ID", "SERVICIO", "ESPECIALIDAD"};
        modelo.setColumnIdentifiers(columnNames);

        // Define el ancho de las columnas
        int[] columnWidths = {50, 200, 200}; // Ancho en píxeles para cada columna
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

        cargarTabla();

    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        ss.listarServicios().forEach(serv -> {

            serv.getEspecialidades().forEach(esp -> {

                modelo.addRow(new Object[]{serv.getId(), serv.getDenominacion(), esp.getDenominacion()});

            });

        });

        if (table.getRowCount() > 0) {

            table.setRowSelectionInterval(0, 0);

        }

    }

}