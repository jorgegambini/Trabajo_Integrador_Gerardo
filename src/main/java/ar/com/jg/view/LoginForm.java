package ar.com.jg.view;

import ar.com.jg.utility.RequestFocusListener;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;


public class LoginForm extends JPanel {

    private JTextField nameField;
    private JPasswordField passwordField;

    public LoginForm(){

        setLayout(new MigLayout("wrap,fillx,insets 5 10 5 10", "[fill,100]"));

        JLabel nameLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Password:");
        nameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        add(nameLabel,"split 2, width 70:70:70, growx");
        add(nameField,"width 150:150:150, pushx");
        add(passwordLabel,"split 2, width 70:70:70, growx");
        add(passwordField,"width 150:150:150,pushx");

        nameField.addAncestorListener( new RequestFocusListener());

    }

    public String getUserName(){

        return nameField.getText();

    }

    public void setUserName(String userName){

        this.nameField.setText(userName);

    }


    public char[] getPassword(){

        return passwordField.getPassword();

    }

    public void setPassword(String password){

        this.passwordField.setText(password);

    }

}
