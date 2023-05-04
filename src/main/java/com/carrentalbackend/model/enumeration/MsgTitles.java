package com.carrentalbackend.model.enumeration;

public enum MsgTitles {

    WARNING_MANAGERS("Incorrect manager number");

    public final String title;
    MsgTitles(String title) {
        this.title = title;
    }
}
