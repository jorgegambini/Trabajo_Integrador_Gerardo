package ar.com.jg.view.accessories;

import ar.com.jg.model.enums.ComunicacionTecnicoCliente;
import ar.com.jg.model.enums.TipoProblema;
import ar.com.jg.utility.RequestFocusListener;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class IngresarComunicacionTecnicoCliente extends JPanel {


    @Getter @Setter
    private ComunicacionTecnicoCliente comunicacionTecnicoCliente;

    private JComboBox<ComunicacionTecnicoCliente> comboField;

    public IngresarComunicacionTecnicoCliente() {

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Informar vía:");
        comboField = new JComboBox<>(ComunicacionTecnicoCliente.values());

        add(textoLabel, "split 2, width 110:110:110, growx");
        add(comboField, "width 200:200:200, pushx, gapright 0");

        comboField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    comunicacionTecnicoCliente = (ComunicacionTecnicoCliente) comboField.getSelectedItem();

                }
            }
        });

        if (comboField.getItemCount() > 0) {

           comunicacionTecnicoCliente = (ComunicacionTecnicoCliente) comboField.getSelectedItem();

        }

        comboField.addAncestorListener( new RequestFocusListener());

    }

}
