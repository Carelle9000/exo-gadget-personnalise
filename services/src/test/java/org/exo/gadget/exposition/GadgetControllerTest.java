package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.GadgetData;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GadgetControllerTest {

    @Mock
    private WeatherService weatherService; // Service météo simulé

    @Mock
    private QuoteService quoteService; // Service de citation simulé

    @Mock
    private DocumentService documentService; // Service de documents simulé

    @InjectMocks
    private GadgetController gadgetController; // Controller à tester

    @Mock
    private Identity identity; // Identité utilisateur simulée

    @BeforeEach
    void setUp() {
        // Configure l'utilisateur courant pour ConversationState
        when(identity.getUserId()).thenReturn("testUser");
        ConversationState.setCurrent(mock(ConversationState.class));
        when(ConversationState.getCurrent().getIdentity()).thenReturn(identity);
    }

    @Test
    void testGadgetControllerReturnsGadgetData() {
        // --- Préparer les données de test ---
        WeatherData weather = new WeatherData("10.0", "20.0");
        weather.setTemperature(25.0);
        weather.setDescription("Soleil");

        Quote quote = new Quote("Reste positif", "Anonymous");

        Document doc = new Document("Document1", new java.util.Date());
        List<Document> documents = Collections.singletonList(doc);

        // --- Stubber les services pour retourner ces données ---
        when(weatherService.getWeather()).thenReturn(weather);
        when(quoteService.getDailyQuote()).thenReturn(quote);
        when(documentService.getRecentDocuments("testUser")).thenReturn(documents);

        // --- Exécuter la méthode du controller ---
        GadgetData gadgetData = gadgetController.getGadgetData();

        // --- Vérifier les résultats ---
        assertThat(gadgetData).isNotNull();
        assertThat(gadgetData.getWelcomeMessage()).isEqualTo("Bonjour testUser!");
        assertThat(gadgetData.getWeather()).isEqualTo(weather);
        assertThat(gadgetData.getQuote()).isEqualTo(quote);
        assertThat(gadgetData.getDocuments()).containsExactly(doc);

        // --- Vérifier que les services ont été appelés ---
        verify(weatherService).getWeather();
        verify(quoteService).getDailyQuote();
        verify(documentService).getRecentDocuments("testUser");
    }

    @Test
    void testGadgetControllerWithNoDocuments() {
        // Stub services
        when(weatherService.getWeather()).thenReturn(new WeatherData("0", "0"));
        when(quoteService.getDailyQuote()).thenReturn(new Quote("Contente-toi", "Auteur"));
        when(documentService.getRecentDocuments("testUser")).thenReturn(Collections.emptyList());

        // Appel du controller
        GadgetData gadgetData = gadgetController.getGadgetData();

        // Vérification
        assertThat(gadgetData.getDocuments()).isEmpty();
    }
}
