package org.exo.gadget.api;

import org.exo.gadget.client.WeatherApiClient;
import org.exo.gadget.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherServiceImplTest {

    private WeatherApiClient weatherApiClientMock;
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        // Création d'un mock pour WeatherApiClient
        weatherApiClientMock = Mockito.mock(WeatherApiClient.class);

        // Injection du mock dans le service
        weatherService = new WeatherServiceImpl(weatherApiClientMock);
    }

    @Test
    void testGetWeather() throws Exception {
        // Simulation d'une réponse JSON retournée par l'API
        String mockJsonResponse = "{"
                + "\"main\":{\"temp\":25.5},"
                + "\"weather\":[{\"description\":\"clear sky\"}]"
                + "}";

        // Définir le comportement du mock
        when(weatherApiClientMock.fetchWeatherData("48.8566", "2.3522"))
                .thenReturn(mockJsonResponse);

        // Exécution de la méthode testée
        WeatherData result = weatherService.getWeather("48.8566", "2.3522");

        // Vérifications
        assertNotNull(result);
        assertEquals(25.5, result.getTemperature());
        assertEquals("clear sky", result.getDescription());

        // Vérifier que le mock a bien été appelé une fois
        verify(weatherApiClientMock, times(1))
                .fetchWeatherData("48.8566", "2.3522");
    }
}
