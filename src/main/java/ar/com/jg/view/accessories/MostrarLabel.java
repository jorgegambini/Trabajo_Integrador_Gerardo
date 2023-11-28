package ar.com.jg.view.accessories;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;


public class MostrarLabel extends JPanel {


    public MostrarLabel(String label, int longLabel, int gap){

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,100]"));

        JLabel labelLabel = new JLabel(label);

        add(labelLabel,"width " + longLabel + ":" + longLabel + ":" + longLabel + ", growx, pushx, gapright " + gap);

    }

}
