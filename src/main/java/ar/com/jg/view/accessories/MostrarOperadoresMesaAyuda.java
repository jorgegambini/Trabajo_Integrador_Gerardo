package ar.com.jg.view.accessories;

import ar.com.jg.services.OperadorMesaAyudaService;
import ar.com.jg.services.OperadorMesaAyudaServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class MostrarOperadoresMesaAyuda extends JPanel {

    private OperadorMesaAyudaService os;

    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel modelo;

    public MostrarOperadoresMesaAyuda(EntityManager em) {

        os = new OperadorMesaAyudaServiceImpl(em);

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        table = new JTable();

        scroll = new JScrollPane(table);
        add(scroll, "width 950:950:950, height 150:150:150");

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

        String[] columnNames = {"LEGAJO", "NOMBRE", "APELLIDO", "USUARIO", "PASSWORD", "TELEFONO", "CELULAR", "EMAIL"};
        modelo.setColumnIdentifiers(columnNames);

        // Define el ancho de las columnas
        int[] columnWidths = {70, 150, 150, 100, 100, 90, 90, 200}; // Ancho en píxeles para cada columna
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

        cargarTabla();

    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        os.listarOperadoresMesaAyuda().forEach(oper -> {

            modelo.addRow(new Object[]{oper.getLegajo(), oper.getNombre(), oper.getApellido(), oper.getUsuario(), oper.getPassword(), oper.getDatosContacto().getTelefono(), oper.getDatosContacto().getCelular(), oper.getDatosContacto().getEmail()});

        });

        if (table.getRowCount() > 0) {

            table.setRowSelectionInterval(0, 0);

        }

    }

}