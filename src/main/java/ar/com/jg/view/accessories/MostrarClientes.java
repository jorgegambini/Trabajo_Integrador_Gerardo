package ar.com.jg.view.accessories;

import ar.com.jg.services.ClienteService;
import ar.com.jg.services.ClienteServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class MostrarClientes extends JPanel {

    private ClienteService cs;

    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel modelo;

    public MostrarClientes(EntityManager em) {

        cs = new ClienteServiceImpl(em);

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        table = new JTable();

        scroll = new JScrollPane(table);
        add(scroll, "width 930:930:930, height 150:150:150");

        dibujarTabla();

        table.addAncestorListener( new RequestFocusListener());

    }

    private void dibujarTabla() {

        modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;

            }

        };

        table.setModel(modelo);

        String[] columnNames = {"CUIT", "RAZON SOCIAL", "TELEFONO", "CELULAR", "EMAIL", "ESPECIALIDAD"};
        modelo.setColumnIdentifiers(columnNames);

        // Define el ancho de las columnas
        int[] columnWidths = {100, 300, 90, 90, 200, 150}; // Ancho en píxeles para cada columna
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

        cargarTabla();

    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        cs.listarClientes().forEach(clie -> {

            clie.getServicios().forEach(serv ->{

                modelo.addRow(new Object[]{clie.getCuit(), clie.getRazonSocial(), clie.getDatosContacto().getTelefono(), clie.getDatosContacto().getCelular(), clie.getDatosContacto().getEmail(), serv.getDenominacion()});

            });

        });

        if (table.getRowCount() > 0) {

            table.setRowSelectionInterval(0, 0);

        }

    }

}