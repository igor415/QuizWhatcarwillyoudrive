package com.varivoda.igor.quiz_whatcarwillyoudrive;

public class DieselPetrolOpcija {
    private int id;
    private String pitanje;
    private String odgovor1;
    private String odgovor2;

    public DieselPetrolOpcija(){}

    public DieselPetrolOpcija(int id, String pitanje, String odgovor1, String odgovor2) {
        this.id = id;
        this.pitanje = pitanje;
        this.odgovor1 = odgovor1;
        this.odgovor2 = odgovor2;
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
}
