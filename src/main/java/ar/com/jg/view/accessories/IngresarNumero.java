package ar.com.jg.view.accessories;

import ar.com.jg.utility.RequestFocusListener;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;


public class IngresarNumero extends JPanel {

    private JTextField numeroField;

    public IngresarNumero(String numLabel, String textField, int longLabel, int longField, int gap, boolean habilitado) {

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel numeroLabel = new JLabel(numLabel);
        numeroField = new JTextField();

        numeroField.setHorizontalAlignment(SwingConstants.RIGHT);
        numeroField.setEnabled(habilitado);
        numeroField.setText(textField);

        add(numeroLabel, "split 2, width " + longLabel + ":" + longLabel + ":" + longLabel + ", growx");
        add(numeroField, "width " + longField + ":" + longField + ":" + longField + ", pushx, gapright " + gap);

        numeroField.addAncestorListener(new RequestFocusListener());
        numeroField.selectAll();

    }

    public String getNumero() {

        return numeroField.getText();

    }

}
