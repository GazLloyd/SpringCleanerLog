package com.gmail.gazllloyd.springcleanerlog.gui;

import com.gmail.gazllloyd.springcleanerlog.LogFile;
import com.gmail.gazllloyd.springcleanerlog.SpringCleanerLog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Gareth Lloyd on 15/05/2015.
 */
public class OpenAction extends AbstractAction {
    JFileChooser filechooser;
    JFrame frame;
    public OpenAction(JFrame f, Icon i) {
        super("Open", i);
        frame = f;
        filechooser = new JFileChooser();
        filechooser.setMultiSelectionEnabled(false);
        filechooser.addChoosableFileFilter(new TxtFilter());
        filechooser.addChoosableFileFilter(new CsvFilter());
        filechooser.addChoosableFileFilter(new SclFilter());
        AllFilter af = new AllFilter();
        filechooser.addChoosableFileFilter(af);
        filechooser.setFileFilter(af);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnvalue = filechooser.showOpenDialog(frame);
        if (returnvalue == JFileChooser.APPROVE_OPTION) {
            File openedfile = filechooser.getSelectedFile();
            SpringCleanerLog.remake(openedfile,frame.getX(),frame.getY());
            //SCFrame f = new SCFrame(l.name, l.items);
            frame.dispose();
            StartFrame.self.setVisible(false);
        }
    }

    private class TxtFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String ext = FileChooserUtils.getExtension(f);
            if (ext != null && ext.equalsIgnoreCase(FileChooserUtils.txt))
                return true;
            return false;
        }

        @Override
        public String getDescription() {
            return ".txt files";
        }
    }

    private class CsvFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String ext = FileChooserUtils.getExtension(f);
            if (ext != null && ext.equalsIgnoreCase(FileChooserUtils.csv))
                return true;
            return false;
        }

        @Override
        public String getDescription() {
            return ".csv files";
        }
    }

    private class SclFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String ext = FileChooserUtils.getExtension(f);
            if (ext != null && ext.equalsIgnoreCase(FileChooserUtils.scl))
                return true;
            return false;
        }

        @Override
        public String getDescription() {
            return ".scl files";
        }
    }

    private class AllFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String ext = FileChooserUtils.getExtension(f);
            if (ext != null && (ext.equalsIgnoreCase(FileChooserUtils.txt) || ext.equalsIgnoreCase(FileChooserUtils.csv) || ext.equalsIgnoreCase(FileChooserUtils.scl)))
                return true;
            return false;
        }

        @Override
        public String getDescription() {
            return "All supported files";
        }
    }

}
