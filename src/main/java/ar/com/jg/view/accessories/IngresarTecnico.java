package ar.com.jg.view.accessories;

import ar.com.jg.model.Servicio;
import ar.com.jg.model.Tecnico;
import ar.com.jg.utility.RequestFocusListener;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;


public class IngresarTecnico extends JPanel {


    @Getter @Setter
    private Tecnico tecnico;
    private Servicio servicio;

    @Getter @Setter
    private int cantidadElementosCombo;

    private JComboBox comboField;

    public IngresarTecnico(Servicio servicio) {

        this.servicio = servicio;

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Técnico:");
        comboField = new JComboBox();

        add(textoLabel, "split 2, width 80:80:80, growx");
        add(comboField, "width 300:300:300, pushx, gapright 0");

        comboField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    tecnico = (Tecnico) comboField.getSelectedItem();

                }
            }
        });

        getTecnicos();

        comboField.addAncestorListener( new RequestFocusListener());

    }

    private void getTecnicos() {

        comboField.removeAllItems();

        Set<Tecnico> tecnicos = new HashSet<>();

        servicio.getEspecialidades().forEach(esp -> esp.getTecnicos().forEach(tec -> tecnicos.add(tec)));

        tecnicos.forEach(tec -> comboField.addItem(tec));

        if (comboField.getItemCount() > 0) {

            tecnico = (Tecnico) comboField.getSelectedItem();

        }

        cantidadElementosCombo = comboField.getItemCount();

    }

}
