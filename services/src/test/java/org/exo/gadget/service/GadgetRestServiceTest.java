package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GadgetRestServiceTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private QuoteService quoteService;

    @Mock
    private DocumentService documentService;

    @Mock
    private ConversationState conversationState;

    @Mock
    private Identity identity;

    private GadgetRestService gadgetRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gadgetRestService = new GadgetRestService(weatherService, quoteService, documentService);
    }

    @Test
    void testGetWeather() {
        WeatherData weather = new WeatherData("Paris", 25, "Ensoleillé");
        when(weatherService.getWeather()).thenReturn(weather);

        Response response = gadgetRestService.getWeather();

        assertEquals(200, response.getStatus());
        assertEquals(weather, response.getEntity());
    }

    @Test
    void testGetQuote() {
        Quote quote = new Quote("La vie est belle");
        when(quoteService.getDailyQuote()).thenReturn(quote);

        Response response = gadgetRestService.getQuote();

        assertEquals(200, response.getStatus());
        assertEquals(quote, response.getEntity());
    }

    @Test
    void testGetDocuments() {
        // Simuler l'utilisateur connecté
        when(ConversationState.getCurrent()).thenReturn(conversationState);
        when(conversationState.getIdentity()).thenReturn(identity);
        when(identity.getUserId()).thenReturn("testUser");

        List<Document> docs = List.of(new Document("Doc1"), new Document("Doc2"));
        when(documentService.getRecentDocuments("testUser")).thenReturn(docs);

        Response response = gadgetRestService.getDocuments();

        assertEquals(200, response.getStatus());
        assertEquals(docs, response.getEntity());
    }
}
