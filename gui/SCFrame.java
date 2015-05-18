package com.gmail.gazllloyd.springcleanerlog.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
    ArrayList<String> order;
    Logger log;
    static int defaultcolumns = 4;
    ArrayList<JTextField> fields;
    static SCFrame self;

    public SCFrame(String m, HashMap<String,SCItem> i, ArrayList<String> o) {
        this(m,i,o,0,0);
    }

    public SCFrame(String m, HashMap<String,SCItem> i, ArrayList<String> o, int x, int y) {
        this.log = SpringCleanerLog.log;
        self = this;
        log.info("Creating gui");
        this.items = i;
        this.order = o;
        this.mob = m;
        this.fields = new ArrayList<JTextField>();

        setLocation(x,y);
        setTitle("Spring Cleaner logger: "+mob);
        setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource(SpringCleanerLog.iconimg)));

        JPanel contentpanel = new JPanel();
        contentpanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setContentPane(contentpanel);
        contentpanel.setBorder(new EmptyBorder(5,5,5,5));

        JMenuBar menubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenu editmenu = new JMenu("Edit");
        JMenu helpmenu = new JMenu("Help");

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

        JCheckBoxMenuItem enableedit = new JCheckBoxMenuItem("Enable manual editing");
        enableedit.setState(true);
        enableedit.setAction(new AbstractAction("Enable manual editing") {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean editable = !fields.get(0).isEditable();
                for (JTextField f : fields) {
                    f.setEditable(editable);
                }
            }
        });

        JMenuItem viewgithub = new JMenuItem("View on GitHub");
        viewgithub.setAction(new AbstractAction("View on GitHub") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(SpringCleanerLog.githuburl));
                } catch (Exception ex) {
                    log.severe("Something went wrong opening URI!\nActionEvent: "+e.paramString());
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SCFrame.self,"Oops! Something went wrong","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JMenuItem about = new JMenuItem("About");
        about.setAction(new AbstractAction("About") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SCFrame.self,
                        "SpringCleanerLog\n" +
                                "Created and maintained by Gaz Lloyd\n" +
                                "http://rs.wikia.com/User:Gaz_Lloyd\n" +
                                SpringCleanerLog.githuburl,
                        "About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource(SpringCleanerLog.iconimg)));
            }
        });

        filemenu.add(open);
        filemenu.add(newmob);
        filemenu.add(exit);

        editmenu.add(enableedit);

        helpmenu.add(viewgithub);
        helpmenu.add(about);

        menubar.add(filemenu);
        menubar.add(editmenu);
        menubar.add(helpmenu);
        setJMenuBar(menubar);

        JLabel moblabel = new JLabel("Spring cleaner log for: " + mob);
        Font f = moblabel.getFont();
        Font f2 = f.deriveFont(Font.BOLD,f.getSize()+2);
        moblabel.setFont(f2);
        moblabel.setBorder(new EmptyBorder(0,5,10,5));
        moblabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 13;
        contentpanel.add(moblabel, c);

        c.anchor = GridBagConstraints.CENTER;
        Font f3 = f.deriveFont(Font.BOLD);
        int row = 1;
        for (String k : order) {
            SCItem item = items.get(k);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = row;
            c.gridx = 0;
            c.gridwidth = 13;
            contentpanel.add(new JSeparator(),c);

            c.gridheight = item.isMultiPartial() ? item.getPartials().size() : 1;
            c.gridwidth = 1;

            JLabel itemlabel = new JLabel(item.getName());
            itemlabel.setBorder(new EmptyBorder(5, 5, 5, 5));
            itemlabel.setFont(f3);
            c.gridy = ++row;
            c.gridx = 0;
            contentpanel.add(itemlabel,c);

            c.gridx = 1;
            contentpanel.add(Box.createRigidArea(new Dimension(10,1)),c);

            JLabel successlabel = new JLabel("Success");
            successlabel.setBorder(new EmptyBorder(5,5,5,5));
            c.gridx = 2;
            contentpanel.add(successlabel,c);

            JTextField successfield = new JTextField(""+item.getSuccess(),defaultcolumns);
            successfield.setAction(new EditAction(successfield, item, CounterType.SUCCESS));
            successfield.setHorizontalAlignment(SwingConstants.RIGHT);
            c.gridx = 3;
            c.fill = GridBagConstraints.NONE;
            contentpanel.add(successfield,c);
            fields.add(successfield);

            JButton successbutton = new JButton("+");
            successbutton.setAction(new IncrementAction(successfield, item, CounterType.SUCCESS));
            c.gridx = 4;
            contentpanel.add(successbutton,c);

            c.gridx = 5;
            contentpanel.add(Box.createRigidArea(new Dimension(10,1)),c);

            JLabel failurelabel = new JLabel("Failure");
            failurelabel.setBorder(new EmptyBorder(5,5,5,5));
            c.gridx = 6;
            c.fill = GridBagConstraints.HORIZONTAL;
            contentpanel.add(failurelabel,c);

            JTextField failurefield = new JTextField(""+item.getFailure(),defaultcolumns);
            failurefield.setAction(new EditAction(failurefield, item, CounterType.FAILURE));
            failurefield.setHorizontalAlignment(SwingConstants.RIGHT);
            c.gridx = 7;
            c.fill = GridBagConstraints.NONE;
            contentpanel.add(failurefield,c);
            fields.add(failurefield);

            JButton failurebutton = new JButton("+");
            failurebutton.setAction(new IncrementAction(failurefield, item, CounterType.FAILURE));
            c.gridx = 8;
            contentpanel.add(failurebutton,c);

            c.gridx = 9;
            contentpanel.add(Box.createRigidArea(new Dimension(10,1)),c);

            if (item.hasNoPartial()) {
                JLabel partiallabel = new JLabel("no partial failure");
                partiallabel.setBorder(new EmptyBorder(5,5,5,5));
                partiallabel.setHorizontalAlignment(SwingConstants.RIGHT);
                c.gridx = 10;
                c.gridwidth = 3;
                c.fill = GridBagConstraints.HORIZONTAL;
                contentpanel.add(partiallabel,c);
                row++;
            }
            else if (item.isMultiPartial()) {
                c.gridheight = 1;
                for (String j : item.getOrder()) {
                    JLabel partiallabel = new JLabel(j);
                    partiallabel.setBorder(new EmptyBorder(5,5,5,5));
                    c.gridx = 10;
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridy = row;
                    contentpanel.add(partiallabel,c);

                    JTextField partialfield = new JTextField("" + item.getPartial(j),defaultcolumns);
                    partialfield.setAction(new EditAction(partialfield, item, j));
                    partialfield.setHorizontalAlignment(SwingConstants.RIGHT);
                    c.gridx = 11;
                    c.fill = GridBagConstraints.NONE;
                    contentpanel.add(partialfield,c);
                    fields.add(partialfield);

                    JButton partialbutton = new JButton("+");
                    partialbutton.setAction(new IncrementAction(partialfield, item, j));
                    c.gridx = 12;
                    contentpanel.add(partialbutton,c);
                    row++;
                }
            } else {
                c.gridx = 10;
                c.gridwidth = 1;

                JLabel partiallabel = new JLabel("Failure");
                partiallabel.setBorder(new EmptyBorder(5,5,5,5));
                c.fill = GridBagConstraints.HORIZONTAL;
                contentpanel.add(partiallabel,c);

                JTextField partialfield = new JTextField("" + item.getPartial(),defaultcolumns);
                partialfield.setAction(new EditAction(partialfield, item, CounterType.PARTIAL));
                partialfield.setHorizontalAlignment(SwingConstants.RIGHT);
                c.gridx = 11;
                c.fill = GridBagConstraints.NONE;
                contentpanel.add(partialfield,c);
                fields.add(partialfield);

                JButton partialbutton = new JButton("+");
                partialbutton.setAction(new IncrementAction(partialfield, item, CounterType.PARTIAL));
                c.gridx = 12;
                contentpanel.add(partialbutton,c);
                row++;
            }

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
    }

}
