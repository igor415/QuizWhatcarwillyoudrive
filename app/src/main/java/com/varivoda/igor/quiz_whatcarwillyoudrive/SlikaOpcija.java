package com.varivoda.igor.quiz_whatcarwillyoudrive;

public class SlikaOpcija {
    private int id;
    private String name;
    private String url;

    public SlikaOpcija(){}

    public SlikaOpcija(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
