package org.exo.gadget.model;

public class WeatherData {
    private String temperature;
    private String description;

    public WeatherData(String temperature, String description) {
        this.temperature = temperature;
        this.description = description;
    }

    // Getters/Setters
    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}