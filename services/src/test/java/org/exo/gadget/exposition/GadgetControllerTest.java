package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.GadgetData;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GadgetControllerTest {

    @Mock
    private WeatherService weatherService;
    @Mock
    private QuoteService quoteService;
    @Mock
    private DocumentService documentService;
    @Mock
    private ConversationState conversationState;
    @Mock
    private Identity securityIdentity;
    private GadgetController controller;

    @BeforeEach
    void setUp() {
        // Initialiser GadgetController avec les mocks
        controller = new GadgetController(weatherService, quoteService, documentService);
        // Configurer ConversationState
        when(securityIdentity.getUserId()).thenReturn("testUser");
        when(conversationState.getIdentity()).thenReturn(securityIdentity);
        ConversationState.setCurrent(conversationState);
    }

    @Test
    void testGetGadgetDataSuccess() {
        // Mock services
        WeatherData weatherData = new WeatherData("Paris",  "Sunny");
        Quote quote = new Quote("Carpe diem", "Horace");
        List<Document> documents = Collections.singletonList(new Document("Test Doc", "2025-08-21 10:00:00"));
        when(weatherService.getWeather(48.8566, 2.3522)).thenReturn(weatherData);
        when(quoteService.getDailyQuote()).thenReturn(quote);
        when(documentService.getRecentDocuments("testUser")).thenReturn(documents);

        Response response = controller.getGadgetData(48.8566, 2.3522);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof GadgetData);
        GadgetData data = (GadgetData) response.getEntity();
        assertEquals("Bienvenue, testUser!", data.getWelcome());
        assertEquals(weatherData, data.getWeather());
        assertEquals(quote, data.getQuote());
        assertEquals(documents, data.getDocuments());
    }

    @Test
    void testGetGadgetDataUnauthorized() {
        // Simuler utilisateur non authentifié
        when(conversationState.getIdentity()).thenReturn(null);
        ConversationState.setCurrent(conversationState);

        Response response = controller.getGadgetData(48.8566, 2.3522);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("Utilisateur non authentifié", response.getEntity());
    }

    @Test
    void testGetGadgetDataNullServices() {
        // Simuler services renvoyant null
        when(weatherService.getWeather(48.8566, 2.3522)).thenReturn(null);
        when(quoteService.getDailyQuote()).thenReturn(null);
        when(documentService.getRecentDocuments("testUser")).thenReturn(null);

        Response response = controller.getGadgetData(48.8566, 2.3522);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof GadgetData);
        GadgetData data = (GadgetData) response.getEntity();
        assertEquals("Bienvenue, testUser!", data.getWelcome());
        assertNull(data.getWeather());
        assertNull(data.getQuote());
        assertNull(data.getDocuments());
    }

    @Test
    void testGetGadgetDataServiceException() {
        // Simuler une exception dans DocumentService
        when(weatherService.getWeather(48.8566, 2.3522)).thenReturn(new WeatherData("Paris",  "Sunny"));
        when(quoteService.getDailyQuote()).thenReturn(new Quote("Carpe diem", "Horace"));
        when(documentService.getRecentDocuments("testUser")).thenThrow(new RuntimeException("Service error"));

        Response response = controller.getGadgetData(48.8566, 2.3522);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof GadgetData);
        GadgetData data = (GadgetData) response.getEntity();
        assertEquals("Bienvenue, testUser!", data.getWelcome());
        assertNotNull(data.getWeather());
        assertNotNull(data.getQuote());
        assertNull(data.getDocuments());
    }
}