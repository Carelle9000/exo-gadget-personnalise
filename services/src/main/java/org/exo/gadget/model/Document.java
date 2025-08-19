package org.exo.gadget.model;

public class Document {
    private String name;
    private String lastModified;

    public Document(String name, String lastModified) {
        this.name = name;
        this.lastModified = lastModified;
    }

    // Getters/Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastModified() { return lastModified; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }
}