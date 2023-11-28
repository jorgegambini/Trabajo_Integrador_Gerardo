package ar.com.jg.view.accessories;

import ar.com.jg.model.Cliente;
import ar.com.jg.model.Servicio;
import ar.com.jg.services.ClienteService;
import ar.com.jg.services.ClienteServiceImpl;
import ar.com.jg.services.ServicioService;
import ar.com.jg.services.ServicioServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class IngresarServicio extends JPanel {

    private ServicioService ss;
    @Getter @Setter
    private Servicio servicio;
    private ClienteService cs;

    private JComboBox comboField;

    @Getter @Setter
    private int cantidadElementosCombo;

    Cliente cliente;

    public IngresarServicio(EntityManager em, Cliente cliente) {

        ss = new ServicioServiceImpl(em);
        cs = new ClienteServiceImpl(em);

        this.cliente = cliente;

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Servicio:");
        comboField = new JComboBox();

        add(textoLabel, "split 2, width 90:90:90, growx");
        add(comboField, "width 250:250:250, pushx, gapright 0");

        comboField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    servicio = (Servicio) comboField.getSelectedItem();

                }
            }
        });

        getServicios();

        comboField.addAncestorListener( new RequestFocusListener());

    }

    private void getServicios() {

        comboField.removeAllItems();

        if(cliente == null) ss.listarServicios().forEach(serv -> comboField.addItem(serv));
        else cliente.getServicios().forEach(serv -> comboField.addItem(serv));

        if (comboField.getItemCount() > 0) {

            servicio = (Servicio) comboField.getSelectedItem();

        }

        cantidadElementosCombo = comboField.getItemCount();

    }

}
