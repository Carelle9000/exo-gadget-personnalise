package org.exo.gadget.api;

import org.exo.gadget.model.WeatherData;

public interface WeatherService {
    WeatherData getWeather(String ipAddress); // Basé sur IP pour position

	WeatherData getWeather(double lat, double lon);
}