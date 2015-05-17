package com.gmail.gazllloyd.springcleanerlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by Gareth Lloyd on 14/05/2015.
 */
public class SCItem extends Observable {
    private String name;
    private int success = 0;
    private int failure = 0;
    private boolean isMultiPartial;
    private int partial = 0;
    private HashMap<String,Integer> partials;

    public SCItem(String name, int success, int failure, int partial) {
        this.name = name;
        this.success = success;
        this.failure = failure;
        this.partial = partial;
        this.isMultiPartial = false;
    }

    public SCItem(String name, int success, int failure, HashMap<String,Integer> partials) {
        this.name = name;
        this.success = success;
        this.failure = failure;
        this.partials = partials;
        this.isMultiPartial = true;
    }

    public static SCItem makeSCItem(String line) {
        String[] linevals = line.split(",");
        return makeSCItem(linevals);
    }

    public static SCItem makeSCItem(String[] linevals) {
        int s=0, f=0, p=0;
        HashMap<String,Integer> ps;

        try {
            s = Integer.parseInt(linevals[1]);
        } catch(NumberFormatException e) {
            s = 0;
        }

        try {
            f = Integer.parseInt(linevals[2]);
        } catch(NumberFormatException e) {
            f = 0;
        }

        try {
            p = Integer.parseInt(linevals[3]);
        } catch(NumberFormatException e) {
            ps = new HashMap<String, Integer>();
            int psi;
            for (int i = 3; i < linevals.length; i+=2  ) {
                psi = 0;
                try {
                    psi = Integer.parseInt(linevals[i+1]);
                } catch(NumberFormatException e1) {
                    psi = 0;
                }
                ps.put(linevals[i], psi);
            }
            return new SCItem(linevals[0], s, f, ps);
        }

        return new SCItem(linevals[0], s, f, p);
    }

    public String[] getWritable() {
        ArrayList<String> w = new ArrayList<String>();
        w.add(name);
        w.add(""+success);
        w.add(""+failure);
        if (isMultiPartial) {
            for (String i : partials.keySet()) {
                w.add(i);
                w.add(partials.get(i).toString());
            }
        }
        else {
            w.add(""+partial);
        }
        String [] a = new String[1];
        return w.toArray(a);
    }

    @Override
    public String toString() {
        return Arrays.toString(getWritable());
    }


    public void setValue(String s, int v) {
        if (s.equalsIgnoreCase("success"))
            success = v;
        else if (s.equalsIgnoreCase("failure"))
            failure = v;
        else if (s.equalsIgnoreCase("partial"))
            partial = v;
        else
            partials.put(s, v);
        update();
    }


    public int increment(String s) {
        if (s.equalsIgnoreCase("success"))
            return incrementSuccess();
        if (s.equalsIgnoreCase("failure"))
            return incrementFailure();
        if (s.equalsIgnoreCase("partial"))
            return incrementPartial();
        return incrementPartial(s);
    }

    public int incrementSuccess() {
        success++;
        update();
        return success;
    }
    public int incrementFailure() {
        failure++;
        update();
        return failure;
    }
    public int incrementPartial() {
        if (isMultiPartial)
            return 0;
        partial++;
        update();
        return partial;
    }
    public int incrementPartial(String p) {
        partials.put(p, partials.get(p)+1);
        update();
        return partials.get(p);
    }

    public void update() {
        setChanged();
        notifyObservers();
    }


    public String getName() {
        return name;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public boolean isMultiPartial() {
        return isMultiPartial;
    }

    public int getPartial() {
        return partial;
    }
    public int getPartial(String p) {
        if (!isMultiPartial)
            return 0;
        return partials.get(p);
    }

    public HashMap<String, Integer> getPartials() {
        return partials;
    }
}
