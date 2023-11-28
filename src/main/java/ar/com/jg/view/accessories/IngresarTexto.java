package ar.com.jg.view.accessories;

import ar.com.jg.utility.RequestFocusListener;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;


public class IngresarTexto extends JPanel {

    private JTextField textoField;

    public IngresarTexto(String textLabel, String textField, int longLabel, int longField, int gap) {

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel(textLabel);
        textoField = new JTextField();

        textoField.setText(textField);

        add(textoLabel, "split 2, width " + longLabel + ":" + longLabel + ":" + longLabel + ", growx");
        add(textoField, "width " + longField + ":" + longField + ":" + longField + ", pushx, gapright " + gap);

        textoField.addAncestorListener(new RequestFocusListener());
        textoField.selectAll();

    }

    public String getTexto() {

        return textoField.getText();

    }

}
