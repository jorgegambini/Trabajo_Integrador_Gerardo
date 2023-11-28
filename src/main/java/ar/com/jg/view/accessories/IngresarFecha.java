package ar.com.jg.view.accessories;

import ar.com.jg.utility.RequestFocusListener;
import datechooser.DateChooser;
import datechooser.listener.DateChooserAction;
import datechooser.listener.DateChooserAdapter;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class IngresarFecha extends JPanel {

    private DateChooser dateChooser;
    private JTextField textoField;
    private JButton cmdDate;
    @Getter @Setter
    private LocalDate dateField;

    public IngresarFecha(String textLabel, LocalDate date, int longLabel, int longField, int gap) {

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,"+ (longLabel + longField + 80)+"]"));



        JLabel textoLabel = new JLabel(textLabel);
        textoField = new JTextField();
        dateChooser = new DateChooser();
        cmdDate = new JButton("...");

        dateChooser.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateChanged(Date date, DateChooserAction action) {

                dateField = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            }
        });

        this.dateField = date;

        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        dateChooser.setTextField(textoField);
        textoField.setEnabled(false);
        textoField.setHorizontalAlignment(SwingConstants.RIGHT);


        add(textoLabel, "split 3, width " + longLabel + ":" + longLabel + ":" + longLabel + ", growx");
        add(textoField, "width " + longField + ":" + longField + ":" + longField);
        add(cmdDate,"width 30:30:30, height 20:20:20, pushx, gapright " + gap);

        cmdDate.addActionListener(e -> dateChooser.showPopup());

        //dateField = date;

        dateChooser.setSelectedDate(Date.from(dateField.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        //dateChooser.addAncestorListener(new RequestFocusListener());

    }

}
