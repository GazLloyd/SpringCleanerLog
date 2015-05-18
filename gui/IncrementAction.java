package com.gmail.gazllloyd.springcleanerlog.gui;

import com.gmail.gazllloyd.springcleanerlog.SCItem;
import com.gmail.gazllloyd.springcleanerlog.SpringCleanerLog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Gareth Lloyd on 15/05/2015.
 */
public class IncrementAction extends AbstractAction {
    JTextField field;
    CounterType type;
    SCItem item;
    String partial;
    public IncrementAction(JTextField f, SCItem i, CounterType t) {
        field = f;
        item = i;
        type = t;
        putValue(NAME, "+");
    }

    public IncrementAction(JTextField f, SCItem i, String  t) {
        field = f;
        item = i;
        type = CounterType.MULTIPARTIAL;
        partial = t;
        putValue(NAME, "+");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int newval = 0;
        switch (type) {
            case SUCCESS:
                newval =  item.incrementSuccess();
                break;
            case FAILURE:
                newval =  item.incrementFailure();
                break;
            case PARTIAL:
                newval =  item.incrementPartial();
                break;
            case MULTIPARTIAL:
                newval = item.incrementPartial(partial);
                break;
        }
        field.setText(""+newval);
        SpringCleanerLog.log.info("Button was pressed! Incremented "+item.getName()+": "+(type == CounterType.MULTIPARTIAL ? partial : type));
    }
}
