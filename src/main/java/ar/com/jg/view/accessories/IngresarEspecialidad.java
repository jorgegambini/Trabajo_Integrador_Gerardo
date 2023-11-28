package ar.com.jg.view.accessories;

import ar.com.jg.model.Especialidad;
import ar.com.jg.services.EspecialidadService;
import ar.com.jg.services.EspecialidadServiceImpl;
import ar.com.jg.utility.RequestFocusListener;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class IngresarEspecialidad extends JPanel {

    private EspecialidadService es;
    @Getter @Setter
    private Especialidad especialidad;

    @Getter @Setter
    private int cantidadElementosCombo;

    private JComboBox comboField;

    public IngresarEspecialidad(EntityManager em) {

        es = new EspecialidadServiceImpl(em);

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel("Especialidad:");
        comboField = new JComboBox();

        add(textoLabel, "split 2, width 90:90:90, growx");
        add(comboField, "width 250:250:250, pushx, gapright 0");

        comboField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    especialidad = (Especialidad) comboField.getSelectedItem();

                }
            }
        });

        getEspecialidades();

        comboField.addAncestorListener( new RequestFocusListener());

    }

    private void getEspecialidades() {

        comboField.removeAllItems();

        es.listarEspecialidades().forEach(esp -> comboField.addItem(esp));

        if (comboField.getItemCount() > 0) {

            especialidad = (Especialidad) comboField.getSelectedItem();

        }

        cantidadElementosCombo = comboField.getItemCount();

    }

}
