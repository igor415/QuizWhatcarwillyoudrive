package com.varivoda.igor.quiz_whatcarwillyoudrive.FirestoreDataObjects;

public class SharedKindOpcija {
    private int id;
    private String pitanje;
    private String odgovor1;
    private String odgovor2;
    private String odgovor3;
    private String odgovor4;

    public SharedKindOpcija(){}

    public SharedKindOpcija(int id, String pitanje, String odgovor1, String odgovor2, String odgovor3, String odgovor4) {
        this.id = id;
        this.pitanje = pitanje;
        this.odgovor1 = odgovor1;
        this.odgovor2 = odgovor2;
        this.odgovor3 = odgovor3;
        this.odgovor4 = odgovor4;
    }

    public int getId() {
        return id;
    }

    public String getPitanje() {
        return pitanje;
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
}
