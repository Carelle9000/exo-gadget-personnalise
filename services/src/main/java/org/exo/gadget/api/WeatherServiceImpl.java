package org.exo.gadget.api;

import org.exo.gadget.client.WeatherApiClient;
import org.exo.gadget.model.WeatherData;
import org.json.JSONObject;

/**
 * Implémentation de l'interface WeatherService
 * --------------------------------------------
 * Cette classe est responsable de la logique métier pour récupérer
 * et interpréter les données météo.
 * <p>
 * Elle utilise WeatherApiClient pour appeler l'API externe OpenWeatherMap.
 */
public record WeatherServiceImpl(WeatherApiClient weatherApiClient) implements WeatherService {

    /**
     * Injection de WeatherApiClient (permet de faciliter les tests unitaires)
     *
     * @param weatherApiClient client HTTP pour consommer l'API OpenWeatherMap
     */
    public WeatherServiceImpl {
    }

    /**
     * Récupère les informations météo pour une latitude et longitude données.
     *
     * @param latitude  la latitude
     * @param longitude la longitude
     * @return un objet WeatherData contenant la température et la description
     * @throws Exception si un problème survient lors de l'appel API ou du parsing
     */
    @Override
    public WeatherData getWeather(String latitude, String longitude) throws Exception {
        // 1. Appel à l'API via WeatherApiClient
        String jsonResponse = weatherApiClient.fetchWeatherData(latitude, longitude);

        // 2. Parser la réponse JSON
        JSONObject json = new JSONObject(jsonResponse);

        double temperature = json.getJSONObject("main").getDouble("temp");
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");

        // 3. Retourner l'objet métier
        return new WeatherData(temperature, description);
    }

    @Override
    public WeatherData getWeatherByCoordinates(String latitude, String longitude) {
        return null;
    }

    @Override
    public WeatherData getWeather() {
        return null;
    }
}
