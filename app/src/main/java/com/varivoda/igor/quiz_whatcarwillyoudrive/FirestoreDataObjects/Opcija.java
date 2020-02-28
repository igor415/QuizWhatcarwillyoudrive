package com.varivoda.igor.quiz_whatcarwillyoudrive.FirestoreDataObjects;

public class Opcija {

    private String naziv;
    private String url;
    private int id;

    //firestore
    public Opcija(){}

    public Opcija(String naziv, String url,int id) {
        this.naziv = naziv;
        this.url = url;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
