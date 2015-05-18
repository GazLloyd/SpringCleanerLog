package com.gmail.gazllloyd.springcleanerlog.gui;

import com.gmail.gazllloyd.springcleanerlog.SpringCleanerLog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * Created by Gareth Lloyd on 15/05/2015.
 */
public class StartFrame extends JFrame {
    Logger log;
    public static StartFrame self;
    public static String nl;

    public StartFrame() {
        this.log = SpringCleanerLog.log;
        log.info("Creating start GUI");
        nl = System.lineSeparator();
        self = this;

        setLocation(SpringCleanerLog.loc.x,SpringCleanerLog.loc.y);
        setTitle("Spring cleaner logger");
        setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource(SpringCleanerLog.iconimg)));
        setResizable(false);

        JTextPane intro = new JTextPane();
        intro.setEditable(false);
        intro.setOpaque(false);
        intro.setText("Welcome to the spring cleaner logger." + nl + "Here you can open an existing log or create a new one:");
        intro.setBorder(new EmptyBorder(5,10,5,10));

        JButton open = new JButton("Open");
        open.setAction(new OpenAction(this, null));
        JButton newmob = new JButton("New",null);
        JButton exit = new JButton("Exit");
        exit.setAction(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        setContentPane(panel);

        JPanel buttonpanel = new JPanel(new FlowLayout());

        panel.add(intro);
        buttonpanel.add(open);
        buttonpanel.add(newmob);
        buttonpanel.add(exit);
        panel.add(buttonpanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new StartFrame();
    }
}
