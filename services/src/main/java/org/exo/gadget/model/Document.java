package org.exo.gadget.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {
    // Getters et Setters
    private String title;
    private String date;

    public Document(String title, String date) {
        this.title = title;
        this.date = date;
    }


}