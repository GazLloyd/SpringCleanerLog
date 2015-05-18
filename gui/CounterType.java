package com.gmail.gazllloyd.springcleanerlog.gui;

/**
 * Created by Gareth Lloyd on 18/05/2015.
 */
public enum CounterType {
    SUCCESS ("Success"),
    FAILURE ("Failure"),
    PARTIAL ("Partial"),
    MULTIPARTIAL ("multi-partial");

    private final String name;

    CounterType(String val) {
        this.name = val;
    }

    public String toString() { return name; }
}
