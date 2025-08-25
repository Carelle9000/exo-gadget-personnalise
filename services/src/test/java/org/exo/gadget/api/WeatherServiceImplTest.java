package org.exo.gadget.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.exo.gadget.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private HttpURLConnection geoConn;
    @Mock
    private HttpURLConnection weatherConn;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Rien à initialiser ici, mockConstruction gère les URLs
    }

    @Test
    void testGetWeatherByIpSuccess() throws Exception {
        // Mock geo API response
        String geoResponse = "{\"latitude\": \"48.8566\", \"longitude\": \"2.3522\"}";
        BufferedReader geoReader = new BufferedReader(new InputStreamReader(new java.io.ByteArrayInputStream(geoResponse.getBytes())));
        when(geoConn.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(geoResponse.getBytes()));
        when(geoConn.getResponseCode()).thenReturn(200);

        // Mock weather API response
        String weatherResponse = "{\"main\": {\"temp\": 20.5}, \"weather\": [{\"description\": \"clear sky\"}]}";
        BufferedReader weatherReader = new BufferedReader(new InputStreamReader(new java.io.ByteArrayInputStream(weatherResponse.getBytes())));
        when(weatherConn.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(weatherResponse.getBytes()));
        when(weatherConn.getResponseCode()).thenReturn(200);

        // Mock URL construction
        try (MockedConstruction<URL> mockedUrl = mockConstruction(URL.class, (mock, context) -> {
            if (context.arguments().get(0).toString().contains("ipapi.co")) {
                when(mock.openConnection()).thenReturn(geoConn);
            } else if (context.arguments().get(0).toString().contains("openweathermap.org")) {
                when(mock.openConnection()).thenReturn(weatherConn);
            }
        })) {
            WeatherData result = weatherService.getWeather("127.0.0.1");

            assertNotNull(result);
            assertEquals(20.5, result.getTemperature());
            assertEquals("clear sky", result.getDescription());
            assertEquals("48.8566", result.getLatitude());
            assertEquals("2.3522", result.getLongitude());
        }
    }

    @Test
    void testGetWeatherByCoordinatesSuccess() throws Exception {
        // Mock weather API response
        String weatherResponse = "{\"main\": {\"temp\": 20.5}, \"weather\": [{\"description\": \"clear sky\"}]}";
        BufferedReader weatherReader = new BufferedReader(new InputStreamReader(new java.io.ByteArrayInputStream(weatherResponse.getBytes())));
        when(weatherConn.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(weatherResponse.getBytes()));
        when(weatherConn.getResponseCode()).thenReturn(200);

        // Mock URL construction
        try (MockedConstruction<URL> mockedUrl = mockConstruction(URL.class, (mock, context) -> {
            when(mock.openConnection()).thenReturn(weatherConn);
        })) {
            WeatherData result = weatherService.getWeather(48.8566, 2.3522);

            assertNotNull(result);
            assertEquals(20.5, result.getTemperature());
            assertEquals("clear sky", result.getDescription());
            assertEquals("48.8566", result.getLatitude());
            assertEquals("2.3522", result.getLongitude());
        }
    }

    @Test
    void testGetWeatherByIpApiError() throws Exception {
        // Mock geo API failure
        when(geoConn.getResponseCode()).thenReturn(500);

        // Mock URL construction
        try (MockedConstruction<URL> mockedUrl = mockConstruction(URL.class, (mock, context) -> {
            when(mock.openConnection()).thenReturn(geoConn);
        })) {
            WeatherData result = weatherService.getWeather("127.0.0.1");

            assertNotNull(result);
            assertEquals(0.0, result.getTemperature());
            assertNull(result.getDescription());
            assertNull(result.getLatitude());
            assertNull(result.getLongitude());
        }
    }

    @Test
    void testGetWeatherByCoordinatesApiError() throws Exception {
        // Mock weather API failure
        when(weatherConn.getResponseCode()).thenReturn(500);

        // Mock URL construction
        try (MockedConstruction<URL> mockedUrl = mockConstruction(URL.class, (mock, context) -> {
            when(mock.openConnection()).thenReturn(weatherConn);
        })) {
            WeatherData result = weatherService.getWeather(48.8566, 2.3522);

            assertNotNull(result);
            assertEquals(0.0, result.getTemperature());
            assertNull(result.getDescription());
            assertEquals("48.8566", result.getLatitude());
            assertEquals("2.3522", result.getLongitude());
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}