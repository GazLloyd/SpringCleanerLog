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
    CounterType type;
    SCItem item;
    String partial;
    public EditAction(JTextField f, SCItem i, String t) {
        field = f;
        item = i;
        partial = t;
        type = CounterType.MULTIPARTIAL;
    }

    public EditAction(JTextField f, SCItem i, CounterType t) {
        field = f;
        item = i;
        type = t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String t = field.getText();
        int v;
        try {
            v = Integer.parseInt(t);
        } catch (NumberFormatException ex) {
            field.setBackground(Color.RED);
            SpringCleanerLog.log.info("Field was edited, but it wasn't a positive integer! Setting to red");
            return;
        }
        if (v < 0) {
            field.setBackground(Color.RED);
            SpringCleanerLog.log.info("Field was edited, but it wasn't a positive integer! Setting to red");
        }
        else {
            field.setBackground(Color.WHITE);
            switch (type) {
                case SUCCESS:
                    item.setSuccess(v);
                    break;
                case FAILURE:
                    item.setFailure(v);
                    break;
                case PARTIAL:
                    item.setPartial(v);
                    break;
                case MULTIPARTIAL:
                    item.setPartial(partial,v);
                    break;
            }
            SpringCleanerLog.log.info("Field was successfully edited! Set "+item.getName()+": "+(type == CounterType.MULTIPARTIAL ? partial : type)+" to "+v);
        }
    }
}
