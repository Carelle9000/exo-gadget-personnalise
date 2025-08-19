package org.exo.gadget.api;

import org.exo.gadget.model.WeatherData;
import org.exo.gadget.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherServiceImpl implements WeatherService {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public WeatherData getWeather(String ipAddress) {
        try {
            // Étape 1: Geo par IP (utiliser API gratuite comme ipapi.co)
            URL geoUrl = new URL("https://ipapi.co/" + ipAddress + "/json/");
            HttpURLConnection geoConn = (HttpURLConnection) geoUrl.openConnection();
            geoConn.setRequestMethod("GET");
            BufferedReader geoIn = new BufferedReader(new InputStreamReader(geoConn.getInputStream()));
            JsonNode geoJson = mapper.readTree(geoIn.readLine());
            String lat = geoJson.get("latitude").asText();
            String lon = geoJson.get("longitude").asText();

            // Étape 2: Météo
            URL weatherUrl = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + Constants.OPENWEATHER_API_KEY + "&units=metric");
            HttpURLConnection weatherConn = (HttpURLConnection) weatherUrl.openConnection();
            weatherConn.setRequestMethod("GET");
            BufferedReader weatherIn = new BufferedReader(new InputStreamReader(weatherConn.getInputStream()));
            JsonNode weatherJson = mapper.readTree(weatherIn.readLine());

            WeatherData data = new WeatherData();
            data.setTemperature(weatherJson.get("main").get("temp").asDouble());
            data.setDescription(weatherJson.get("weather").get(0).get("description").asText());
            return data;
        } catch (Exception e) {
            // Handle error, return default
            return new WeatherData();
        }
    }
}