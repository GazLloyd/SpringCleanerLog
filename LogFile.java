package com.gmail.gazllloyd.springcleanerlog;

import com.gmail.gazllloyd.springcleanerlog.gui.SCFrame;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Gareth Lloyd on 14/05/2015.
 */
public class LogFile implements Observer {
    public static LogFile logfile;
    static Logger log = SpringCleanerLog.log;
    public File file;
    public HashMap<String,SCItem> items;
    public String name;
    public SCFrame frame;

    public LogFile(String path) {
        this(new File(path));
    }

    public LogFile(File f) {
        log.info("created logfile");
        logfile = this;
        file = f;
        setup();
    }

    private void setup() {
        log.info("setting up stuff");
        CSVReader r;
        try {
            r = new CSVReader(new FileReader(file));
            String[] nextline;
            SCItem item;
            items = new HashMap<String, SCItem>();
            while ((nextline = r.readNext()) != null) {
                item = SCItem.makeSCItem(nextline);
                item.addObserver(this);
                items.put(item.getName(),item);
            }
        } catch(Exception e) {
            log.severe("Could not load file!");
            e.printStackTrace();
            return;
        }
        String fn = file.getName();
        fn = fn.substring(0,fn.lastIndexOf('.'));
        name = fn;
    }

    private List<String[]> toWritable() {
        ArrayList<String[]> w = new ArrayList<String[]>();
        for (String i : items.keySet()) {
            w.add(items.get(i).getWritable());
        }
        return w;
    }

    @Override
    public void update(Observable o, Object arg) {
        log.info("Writing CSV");
        CSVWriter w;
        try {
            w = new CSVWriter(new FileWriter(file));
            w.writeAll(toWritable());
            w.close();
        } catch(Exception e) {
            log.severe("Could not write to file!");
            e.printStackTrace();
            return;
        }
    }
}
