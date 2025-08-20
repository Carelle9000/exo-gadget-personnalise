package org.exo.gadget.model;

public class Document {
    private String title;
    private String date;

    public Document(String title, String date) {
        this.title = title;
        this.date = date;
    }

    // Getters et Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}