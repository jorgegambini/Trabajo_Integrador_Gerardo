package ar.com.jg.utility;

import javax.swing.*;
import javax.swing.event.*;


public class RequestFocusListener implements AncestorListener
{
    private boolean removeListener;

    public RequestFocusListener() {

        this(true);

    }

    public RequestFocusListener(boolean removeListener) {

        this.removeListener = removeListener;

    }


    public void ancestorAdded(AncestorEvent e) {

        JComponent component = e.getComponent();
        component.requestFocusInWindow();

        if (removeListener) component.removeAncestorListener( this );

    }

    public void ancestorMoved(AncestorEvent e) {}

    public void ancestorRemoved(AncestorEvent e) {}

}
