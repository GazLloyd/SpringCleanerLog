package com.gmail.gazllloyd.springcleanerlog;

import com.gmail.gazllloyd.springcleanerlog.gui.SCFrame;
import com.gmail.gazllloyd.springcleanerlog.gui.StartFrame;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Gareth Lloyd on 14/05/2015.
 */
public class SpringCleanerLog {
    public static Logger log = Logger.getLogger("SCLogger");
    public LogFile l;
    public SCFrame f;
    public static SpringCleanerLog self;

    public static SpringCleanerLog remake(File file, int x, int y) {
        return new SpringCleanerLog(file,x,y);
    }

    public SpringCleanerLog(File file) {
        this(file, 0,0);
    }

    public SpringCleanerLog(File file, int x, int y) {
        self = this;
        l = new LogFile(file);
        f = new SCFrame(l.name,l.items,x,y);
    }

    public static void main(String args[]) {
        log.config("Setting up options...");
        Options options = new Options();
        options.addOption("f", true, "file to open");

        File input = null;

        if (args.length == 1) {
            input = new File(args[0]);
        }
        else {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = null;
            try {
                cmd = parser.parse(options, args);
                if (cmd.hasOption('f'))
                    input = new File(cmd.getOptionValue('f'));
            } catch (Exception e) {
                log.severe("Failed to parse command line!");
            }
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            log.info("Obtained system look and feel");
        }

        catch(Exception e) {
            log.severe("Failed to get system look and feel");
            e.printStackTrace();
        }

        if (input != null)
            remake(input,0,0);
        else
            new StartFrame();
    }
}
