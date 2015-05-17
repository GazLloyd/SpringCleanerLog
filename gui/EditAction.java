package com.gmail.gazllloyd.springcleanerlog.gui;

import com.gmail.gazllloyd.springcleanerlog.SCItem;
import com.gmail.gazllloyd.springcleanerlog.SpringCleanerLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Gareth Lloyd on 15/05/2015.
 */
public class EditAction extends AbstractAction {
    JTextField field;
    String thing;
    SCItem item;
    public EditAction(JTextField f, SCItem i, String t) {
        field = f;
        item = i;
        thing = t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String t = field.getText();
        int v;
        try {
            v = Integer.parseInt(t);
        } catch (NumberFormatException ex) {
            field.setBackground(Color.RED);
            return;
        }
        if (v < 0) {
            field.setBackground(Color.RED);
        }
        else {
            field.setBackground(Color.WHITE);
            item.setValue(thing,v);
        }
    }
}
