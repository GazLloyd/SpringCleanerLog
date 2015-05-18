package com.gmail.gazllloyd.springcleanerlog.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.logging.Logger;

import com.gmail.gazllloyd.springcleanerlog.LogFile;
import com.gmail.gazllloyd.springcleanerlog.SCItem;
import com.gmail.gazllloyd.springcleanerlog.SpringCleanerLog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Created by Gareth Lloyd on 14/05/2015.
 */
public class SCFrame extends JFrame {
    String mob;
    HashMap<String,SCItem> items;
    Logger log;
    static int defaultcolumns = 4;

    public SCFrame(String m, HashMap<String,SCItem> i) {
        this(m,i,0,0);
    }

    public SCFrame(String m, HashMap<String,SCItem> i, int x, int y) {
        this.log = SpringCleanerLog.log;
        log.info("Creating gui");
        this.items = i;
        this.mob = m;

        setLocation(x,y);
        setTitle("Spring Cleaner logger: "+mob);

        JPanel contentpanel = new JPanel();
        contentpanel.setLayout(new BoxLayout(contentpanel,BoxLayout.Y_AXIS));
        setContentPane(contentpanel);
        contentpanel.setBorder(new EmptyBorder(5,5,5,5));

        JMenuBar menubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.setAction(new OpenAction(this, UIManager.getIcon("FileView.directoryIcon")));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem newmob = new JMenuItem("New",UIManager.getIcon("FileView.fileIcon"));
        newmob.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAction(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        filemenu.add(open);
        filemenu.add(newmob);
        filemenu.add(exit);
        menubar.add(filemenu);
        this.setJMenuBar(menubar);


        JPanel mobpanel = new JPanel();
        JLabel moblabel = new JLabel("Spring cleaner log for: " + mob);
        mobpanel.add(moblabel);
        contentpanel.add(mobpanel);

        for (String k : items.keySet()) {
            contentpanel.add(new JSeparator());

            SCItem item = items.get(k);
            JPanel itempanel = new JPanel();
            itempanel.setLayout(new BoxLayout(itempanel, BoxLayout.X_AXIS));
            //itempanel.setAlignmentX(CENTER_ALIGNMENT);
            //itempanel.setAlignmentY(CENTER_ALIGNMENT);
            JLabel itemlabel = new JLabel(item.getName());
            itemlabel.setBorder(new EmptyBorder(5, 5, 5, 5));

            JPanel labelpanel = new JPanel();
            labelpanel.setLayout(new BoxLayout(labelpanel, BoxLayout.Y_AXIS));
            labelpanel.add(Box.createVerticalGlue());
            labelpanel.add(itemlabel);
            labelpanel.add(Box.createVerticalGlue());
            itempanel.add(labelpanel);
            itempanel.add(Box.createHorizontalGlue());

            JPanel itempanelsuccess = new JPanel(new FlowLayout());
            //itempanelsuccess.setAlignmentX(CENTER_ALIGNMENT);
            //itempanelsuccess.setAlignmentY(CENTER_ALIGNMENT);
            itempanelsuccess.add(new JLabel("Success"));
            JTextField successfield = new JTextField(""+item.getSuccess(),defaultcolumns);
            //successfield.setAlignmentX(CENTER_ALIGNMENT);
            //successfield.setAlignmentY(CENTER_ALIGNMENT);
            successfield.setAction(new EditAction(successfield, item, "success"));
            itempanelsuccess.add(successfield);
            JButton successbutton = new JButton("+");
            successbutton.setAction(new IncrementAction(successfield, item, "success"));
            itempanelsuccess.add(successbutton);

            JPanel itempanelsuccessholder = new JPanel();
            itempanelsuccessholder.setLayout(new BoxLayout(itempanelsuccessholder,BoxLayout.Y_AXIS));
            itempanelsuccessholder.add(Box.createVerticalGlue());
            itempanelsuccessholder.add(itempanelsuccess);
            itempanelsuccessholder.add(Box.createVerticalGlue());
            itempanel.add(itempanelsuccessholder);

            JPanel itempanelfailure = new JPanel(new FlowLayout());
            //itempanelfailure.setAlignmentX(CENTER_ALIGNMENT);
            //itempanelfailure.setAlignmentY(CENTER_ALIGNMENT);
            itempanelfailure.add(new JLabel("Failure"));
            JTextField failurefield = new JTextField(""+item.getFailure(),defaultcolumns);
            failurefield.setAction(new EditAction(failurefield, item, "failure"));
            itempanelfailure.add(failurefield);
            JButton failurebutton = new JButton("+");
            failurebutton.setAction(new IncrementAction(failurefield, item, "failure"));
            itempanelfailure.add(failurebutton);

            JPanel itempanelfailureholder = new JPanel();
            itempanelfailureholder.setLayout(new BoxLayout(itempanelfailureholder,BoxLayout.Y_AXIS));
            itempanelfailureholder.add(Box.createVerticalGlue());
            itempanelfailureholder.add(itempanelfailure);
            itempanelfailureholder.add(Box.createVerticalGlue());
            itempanel.add(itempanelfailureholder);


            JPanel itempanelpartial;
            if (item.hasNoPartial()) {
                itempanelpartial = new JPanel();
                itempanel.add(Box.createHorizontalGlue());
                itempanelpartial.add(new JLabel("no partial failure"));
            }
            else if (item.isMultiPartial()) {
                itempanelpartial = new JPanel();
                itempanelpartial.setLayout(new BoxLayout(itempanelpartial, BoxLayout.Y_AXIS));
                //itempanelpartial.setAlignmentY(CENTER_ALIGNMENT);
                //itempanelpartial.setAlignmentX(CENTER_ALIGNMENT);
                itempanelpartial.add(Box.createVerticalGlue());
                for (String j : item.getPartials().keySet()) {
                    JPanel itempanelpartials = new JPanel();
                    itempanelpartials.setLayout(new BoxLayout(itempanelpartials, BoxLayout.X_AXIS));
                    itempanelpartials.add(Box.createHorizontalGlue());
                    itempanelpartials.add(new JLabel(j));
                    JTextField partialfield = new JTextField("" + item.getPartial(j),defaultcolumns);
                    partialfield.setMaximumSize(new Dimension(50,20));
                    partialfield.setAction(new EditAction(partialfield, item, j));
                    itempanelpartials.add(partialfield);
                    JButton partialbutton = new JButton("+");
                    partialbutton.setAction(new IncrementAction(partialfield, item, j));
                    itempanelpartials.add(partialbutton);
                    itempanelpartial.add(itempanelpartials);
                }
                itempanelpartial.add(Box.createVerticalGlue());
            } else {
                itempanelpartial = new JPanel();
                itempanelpartial.setLayout(new BoxLayout(itempanelpartial, BoxLayout.Y_AXIS));
                //itempanelpartial.setAlignmentY(CENTER_ALIGNMENT);
                //itempanelpartial.setAlignmentX(CENTER_ALIGNMENT);
                itempanelpartial.add(Box.createVerticalGlue());
                JPanel itempanelpartialinner = new JPanel(new FlowLayout());
                //itempanelpartialinner.setAlignmentY(CENTER_ALIGNMENT);
                //itempanelpartialinner.setAlignmentX(CENTER_ALIGNMENT);
                itempanelpartialinner.add(new JLabel("Partial"));
                JTextField partialfield = new JTextField("" + item.getPartial(),defaultcolumns);
                partialfield.setAction(new EditAction(partialfield, item, "partial"));
                itempanelpartialinner.add(partialfield);
                JButton partialbutton = new JButton("+");
                partialbutton.setAction(new IncrementAction(partialfield, item, "partial"));
                itempanelpartialinner.add(partialbutton);
                itempanelpartial.add(itempanelpartialinner);
                itempanelpartial.add(Box.createVerticalGlue());
            }
            itempanel.add(itempanelpartial);

            contentpanel.add(itempanel);

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
    }

}
