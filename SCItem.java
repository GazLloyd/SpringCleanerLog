package com.gmail.gazllloyd.springcleanerlog;

import com.gmail.gazllloyd.springcleanerlog.gui.CounterType;

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
    private PartialType partialtype;
    private int partial = 0;
    private HashMap<String,Integer> partials;
    private ArrayList<String> order;

    public SCItem(String name, int success, int failure) {
        this.name = name;
        this.success = success;
        this.failure = failure;
        this.partialtype = PartialType.NO_PARTIAL;
    }

    public SCItem(String name, int success, int failure, int partial) {
        this.name = name;
        this.success = success;
        this.failure = failure;
        this.partial = partial;
        this.partialtype = PartialType.ONE_PARTIAL;
    }

    public SCItem(String name, int success, int failure, HashMap<String,Integer> partials, ArrayList<String> order) {
        this.name = name;
        this.success = success;
        this.failure = failure;
        this.partials = partials;
        this.order = order;
        this.partialtype = PartialType.MULTI_PARTIAL;
    }

    public static SCItem makeSCItem(String line) {
        String[] linevals = line.split(",");
        return makeSCItem(linevals);
    }

    public static SCItem makeSCItem(String[] linevals) {
        int s=0, f=0, p=0; //successes, failures, singlepartials
        String n = linevals[0]; //name
        HashMap<String,Integer> ps; //multipartials
        ArrayList<String> o; //order

        try {
            s = Integer.parseInt(linevals[1]);
        } catch(NumberFormatException|ArrayIndexOutOfBoundsException e) {
            s = 0;
        }

        try {
            f = Integer.parseInt(linevals[2]);
        } catch(NumberFormatException|ArrayIndexOutOfBoundsException e) {
            f = 0;
        }

        switch(linevals.length) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new SCItem(n,s,f); //name, success, failure = no partials
            case 4:
                try {
                    p = Integer.parseInt(linevals[3]);
                } catch (NumberFormatException e)  {
                    p = 0;
                }
                return new SCItem(n,s,f,p); //name, success, failure, onepartial
            default:
                ps = new HashMap<String, Integer>();
                o = new ArrayList<String>();
                for (int i = 3; i < linevals.length; i+=2  ) {
                    try {
                        p = Integer.parseInt(linevals[i+1]);
                    } catch(NumberFormatException e) {
                        p = 0;
                    }
                    ps.put(linevals[i], p);
                    o.add(linevals[i]);
                }
                return new SCItem(n,s,f,ps,o); //name, success, failure, multiple partial states, order of those
        }
    }

    public String[] getWritable() {
        ArrayList<String> w = new ArrayList<String>();
        w.add(name);
        w.add(""+success);
        w.add(""+failure);
        switch (partialtype) {
            case NO_PARTIAL:
                break;
            case ONE_PARTIAL:
                w.add(""+partial);
                break;
            case MULTI_PARTIAL:
                for (String i : order) {
                    w.add(i);
                    w.add(partials.get(i).toString());
                }
                break;
        }
        return w.toArray(new String[1]);
    }

    @Override
    public String toString() {
        return Arrays.toString(getWritable());
    }


    public void setValue(CounterType s, int v) {
        switch (s) {
            case SUCCESS:
                setSuccess(v);
                break;
            case FAILURE:
                setFailure(v);
                break;
            case PARTIAL:
                setPartial(v);
                break;
        }
    }


    public int increment(CounterType s) {
        switch (s) {
            case SUCCESS:
                return incrementSuccess();
            case FAILURE:
                return incrementFailure();
            case PARTIAL:
                return incrementPartial();
        }
        return 0;
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
        if (partialtype != PartialType.ONE_PARTIAL)
            return 0;
        partial++;
        update();
        return partial;
    }
    public int incrementPartial(String p) {
        if (partialtype != PartialType.MULTI_PARTIAL)
            return 0;
        partials.put(p, partials.get(p)+1);
        update();
        return partials.get(p);
    }


    public void setSuccess(int success) {
        this.success = success;
        update();
    }

    public void setFailure(int failure) {
        this.failure = failure;
        update();
    }

    public void setPartial(int partial) {
        this.partial = partial;
        update();
    }

    public void setPartial(String p, int v) {
        this.partials.put(p,v);
        update();
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getOrder() {
        return order;
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
        return partialtype == PartialType.MULTI_PARTIAL;
    }
    public boolean hasNoPartial() {
        return partialtype == PartialType.NO_PARTIAL;
    }
    public boolean hasOnePartial() {
        return partialtype == PartialType.ONE_PARTIAL;
    }

    public PartialType getPartialtype() {
        return partialtype;
    }

    public int getPartial() {
        return partial;
    }
    public int getPartial(String p) {
        if (partialtype != PartialType.MULTI_PARTIAL)
            return 0;
        return partials.get(p);
    }

    public HashMap<String, Integer> getPartials() {
        return partials;
    }
}
