package ar.com.jg.view.accessories;

import ar.com.jg.model.Cliente;
import ar.com.jg.services.ClienteService;
import ar.com.jg.services.ClienteServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class IngresarCliente extends JPanel {

    private ClienteService cs;
    @Getter @Setter
    private Cliente cliente;
    @Getter @Setter
    private int cantidadElementosCombo;

    private JComboBox comboField;


    public IngresarCliente(EntityManager em) {

        cs = new ClienteServiceImpl(em);

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Cliente:");
        comboField = new JComboBox();

        add(textoLabel, "split 2, width 60:60:60, growx");
        add(comboField, "width 350:350:350, pushx, gapright 0");

        comboField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    cliente = (Cliente) comboField.getSelectedItem();

                }
            }
        });

        getClientes();

        comboField.addAncestorListener( new RequestFocusListener());

    }

    private void getClientes() {

        comboField.removeAllItems();

        cs.listarClientes().forEach(esp -> comboField.addItem(esp));

        if (comboField.getItemCount() > 0) {

            cliente = (Cliente) comboField.getSelectedItem();

        }

        cantidadElementosCombo = comboField.getItemCount();

    }

}
