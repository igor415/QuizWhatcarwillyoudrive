package com.varivoda.igor.quiz_whatcarwillyoudrive;

public class EasyCarOpcija {
    private int id;
    private String odgovor1;
    private String odgovor2;
    private String odgovor3;
    private String odgovor4;
    private String tocanOdgovor;
    private String url;

    public EasyCarOpcija(){}

    public EasyCarOpcija(int id, String odgovor1, String odgovor2, String odgovor3, String odgovor4, String tocanOdgovor, String url) {
        this.id = id;
        this.odgovor1 = odgovor1;
        this.odgovor2 = odgovor2;
        this.odgovor3 = odgovor3;
        this.odgovor4 = odgovor4;
        this.tocanOdgovor = tocanOdgovor;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getOdgovor1() {
        return odgovor1;
    }

    public String getOdgovor2() {
        return odgovor2;
    }

    public String getOdgovor3() {
        return odgovor3;
    }

    public String getOdgovor4() {
        return odgovor4;
    }

    public String getTocanOdgovor() {
        return tocanOdgovor;
    }

    public String getUrl() {
        return url;
    }
}
