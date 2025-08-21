package org.exo.gadget.model;

import java.util.List;

public class GadgetData {
    private String welcomeMessage;
    private String dateTime; // Géré frontend
    private WeatherData weather;
    private Quote quote;
    private List<Document> documents;

    // Constructeur, Getters/Setters
    public GadgetData(String welcomeMessage, WeatherData weather, Quote quote, List<Document> documents) {
        this.welcomeMessage = welcomeMessage;
        this.weather = weather;
        this.quote = quote;
        this.documents = documents;
    }

	public Object getWeather() {
		// TODO Auto-generated method stub
		return weather;
	}

	public Object getWelcome() {
		// TODO Auto-generated method stub
		return welcomeMessage;
	}

	public Object getQuote() {
		// TODO Auto-generated method stub
		return quote;
	}

	public Object getDocuments() {
		// TODO Auto-generated method stub
		return documents;
	}
   
}