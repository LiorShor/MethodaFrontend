package com.example.methodafrontend.Model;

public class Transition {
    private final String id;
    private final String from;
    private final String to;

    public Transition(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;

    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
