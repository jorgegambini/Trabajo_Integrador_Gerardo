package ar.com.jg.view.accessories;

import ar.com.jg.utility.RequestFocusListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


public class IngresarAreaTexto extends JPanel {

    private JScrollPane scroll;
    private JTextArea textoField;

    public IngresarAreaTexto(String textLabel, String textField, int longLabel, int longField) {

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,50]"));

        JLabel textoLabel = new JLabel(textLabel);
        textoField = new JTextArea(10,10);

        textoField.setText(textField);
        scroll = new JScrollPane(textoField);

        add(textoLabel, "width " + longLabel + ":" + longLabel + ":" + longLabel + ", growx");
        add(scroll, "width " + longField + ":" + longField + ":" + longField + ", height 150:150:150");

        textoField.addAncestorListener( new RequestFocusListener());
        textoField.selectAll();

    }

    public String getTexto() {

        return textoField.getText();

    }

}
