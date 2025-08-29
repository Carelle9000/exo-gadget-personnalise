package org.exo.gadget.service;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.exposition.GadgetRestService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.Calendar;
import java.util.Date;
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

    private org.exo.gadget.exposition.GadgetRestService gadgetRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gadgetRestService = new GadgetRestService(weatherService, quoteService, documentService);
    }

    @Test
    void testGetWeather() {
        WeatherData weather = new WeatherData("Paris",  "Ensoleillé");
        when(weatherService.getWeather()).thenReturn(weather);

        Response response = gadgetRestService.getWeather();

        assertEquals(200, response.getStatus());
        assertEquals(weather, response.getEntity());
    }

    @Test
    void testGetQuote() {
        Quote quote = new Quote("La vie est belle", "Anonymous");
        when(quoteService.getDailyQuote()).thenReturn(quote);

        Response response = gadgetRestService.getQuote();

        assertEquals(200, response.getStatus());
        assertEquals(quote, response.getEntity());
    }

    @Test
    void testGetRecentsDocuments() {
        List<Document> docs = List.of(
                new Document("Doc1", new Date(124, Calendar.MAY, 12)),
                new Document("Doc2", new Date(124, Calendar.MAY, 12))
        );

        try (MockedStatic<ConversationState> mocked = mockStatic(ConversationState.class)) {
            mocked.when(ConversationState::getCurrent).thenReturn(conversationState);

            when(conversationState.getIdentity()).thenReturn(identity);
            when(identity.getUserId()).thenReturn("testUser");
            when(documentService.getRecentDocuments("testUser")).thenReturn(docs);

            Response response = gadgetRestService.getDocuments();

            assertEquals(200, response.getStatus());
            assertEquals(docs, response.getEntity());
        }
    }
}
