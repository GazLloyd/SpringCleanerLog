package com.gmail.gazllloyd.springcleanerlog.gui;

import com.gmail.gazllloyd.springcleanerlog.SCItem;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Gareth Lloyd on 15/05/2015.
 */
public class IncrementAction extends AbstractAction {
    JTextField field;
    String thing;
    SCItem item;
    public IncrementAction(JTextField f, SCItem i, String t) {
        field = f;
        item = i;
        thing = t;
        putValue(NAME, "+");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        field.setText(""+item.increment(thing));

    }
}
