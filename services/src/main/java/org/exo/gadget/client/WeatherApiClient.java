package org.exo.gadget.client;

//import org.exo.gadget.model.WeatherData;
import org.exo.gadget.util.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe WeatherApiClient
 * -------------------------
 * Cette classe est responsable d'effectuer l'appel HTTP à l'API OpenWeatherMap
 * et de récupérer les données météo brutes sous forme JSON.
 * Rôle principal :
 * - Construire l'URL avec les paramètres latitude/longitude et clé API
 * - Envoyer la requête GET à l'API
 * - Récupérer la réponse JSON sous forme de String
 * Elle ne fait pas le parsing complet dans cette version (cela pourrait être délégué à un parser JSON).
 */
public class WeatherApiClient {

    /**
     * Récupère les données météo au format JSON depuis l'API OpenWeatherMap
     *
     * @param latitude  la latitude du lieu
     * @param longitude la longitude du lieu
     * @return la réponse JSON brute de l'API OpenWeatherMap
     * @throws Exception si une erreur survient lors de la connexion ou de la lecture
     */
    public String fetchWeatherData(String latitude, String longitude) throws Exception {
        // Construire l'URL de la requête avec la clé API et les coordonnées
        String urlString = Constants.WEATHER_API_URL
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + Constants.OPENWEATHER_API_KEY
                + "&units=metric"; // On récupère les données en Celsius

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Définir la méthode HTTP GET
        conn.setRequestMethod("GET");

        // Lire la réponse
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString(); // Renvoie le JSON brut
    }

    public Object fetchWeatherData(String weatherApiUrl, String latitude, String longitude, String openweatherApiKey) {
        return null;
    }
}
