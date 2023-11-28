package ar.com.jg.view.accessories;

import ar.com.jg.model.enums.EstadoProblema;
import ar.com.jg.utility.RequestFocusListener;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;


public class IngresarEstadoProblema extends JPanel {


    @Getter @Setter
    private EstadoProblema estadoProblema;
    private boolean estadoCombo;
    private JComboBox<EstadoProblema> comboField;

    public IngresarEstadoProblema(EstadoProblema estadoProblema, Boolean estadoCombo) {

        this.estadoProblema = estadoProblema;
        this.estadoCombo = estadoCombo;

        init();

    }

    private void init(){


        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Estado Reporte:");
        if(!this.estadoCombo)comboField = new JComboBox<>(EstadoProblema.values());
        else comboField = new JComboBox<>(EstadoProblema.getOptionsTecnico().toArray(EstadoProblema[]::new));

        comboField.setEnabled(estadoCombo);
        comboField.setSelectedItem(estadoProblema);

        comboField.addItemListener(e -> {

            if (e.getStateChange() == ItemEvent.SELECTED) {

                estadoProblema = (EstadoProblema) comboField.getSelectedItem();

            }

        });

        add(textoLabel, "split 2, width 110:110:110, growx");
        add(comboField, "width 200:200:200, pushx, gapright 0");

        if (comboField.getItemCount() > 0) {

            estadoProblema = (EstadoProblema) comboField.getSelectedItem();

        }

        comboField.addAncestorListener(new RequestFocusListener());

    }

}
