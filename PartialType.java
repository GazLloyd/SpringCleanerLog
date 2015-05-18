package com.gmail.gazllloyd.springcleanerlog;

/**
 * Created by Gareth Lloyd on 18/05/2015.
 */
public enum PartialType {
    NO_PARTIAL (0),
    ONE_PARTIAL (1),
    MULTI_PARTIAL (2);

    private final int val;

    PartialType(int val) {
        this.val = val;
    }

    public String toString() { return ""+val; }
}
