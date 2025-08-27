package org.exo.gadget.api;

import org.exo.gadget.model.WeatherData;

/**
 * Interface WeatherService
 * -------------------------
 * Déclare les méthodes nécessaires pour interagir avec les données météorologiques.
 * Cette abstraction permet de changer facilement l'implémentation (ex. : appel d'une API,
 * récupération en base de données, simulation pour des tests, etc.)
 */
public interface WeatherService {

    WeatherData getWeather(String latitude, String longitude) throws Exception;

    /**
     * Récupère les informations météorologiques pour une latitude et une longitude données.
     *
     * @param latitude  Coordonnée géographique (ex : "3.848")
     * @param longitude Coordonnée géographique (ex : "11.502")
     * @return un objet WeatherData contenant la température, la description, et les coordonnées
     */
    WeatherData getWeatherByCoordinates(String latitude, String longitude);

    WeatherData getWeather();
}
