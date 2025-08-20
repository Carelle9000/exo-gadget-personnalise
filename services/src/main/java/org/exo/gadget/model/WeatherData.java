package org.exo.gadget.model;

public class WeatherData {
	private double temperature;
    private String description;
    private String latitude;
    private String longitude;
    
   //constructeurs 
    public WeatherData() {
  		super();
  		
  	}

    public WeatherData(String latitude, String longitude) {
        this.temperature = 0.0;
        this.description = null;
        this.latitude = latitude;
        this.longitude = longitude;
    }

 // Getters/Setters
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLatitude() { return latitude;}
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude;}
    public void setLongitude(String longitude) { this.longitude = longitude; }

}