package org.exo.gadget.api;

import org.exo.gadget.client.QuoteApiClient;
import org.exo.gadget.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitaire pour QuoteServiceImpl
 * -----------------------------------
 * Ici, on simule le comportement de QuoteApiClient pour éviter les appels réseau réels.
 */
@ExtendWith(MockitoExtension.class)
class QuoteServiceImplTest {

    @Mock
    private QuoteApiClient quoteApiClient; // mock du client API

    @InjectMocks
    private QuoteServiceImpl quoteService; // service à tester

    private Quote sampleQuote;

    @BeforeEach
    void setUp() {
        // Citation exemple utilisée pour les tests
        sampleQuote = new Quote("Stay positive", "Anonymous");
    }

    /**
     * Test du scénario de succès : QuoteApiClient renvoie une citation valide
     */
    @Test
    void testGetDailyQuoteSuccess() throws Exception {
        // Simulation du comportement du mock
        when(quoteApiClient.fetchQuote()).thenReturn(sampleQuote);

        // Appel de la méthode testée
        Quote result = quoteService.getDailyQuote();

        // Vérifications
        assertNotNull(result);
        assertEquals("Stay positive", result.getContent());
        assertEquals("Anonymous", result.getAuthor());

        // Vérifie que le mock a été appelé exactement une fois
        verify(quoteApiClient, times(1)).fetchQuote();
    }

    /**
     * Test du scénario où QuoteApiClient lance une exception (erreur réseau)
     */
    @Test
    void testGetDailyQuoteThrowsException() throws Exception {
        // Simulation d'une exception
        when(quoteApiClient.fetchQuote()).thenThrow(new RuntimeException("Network error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> quoteService.getDailyQuote());

        assertEquals("Erreur récupération citation", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("Network error", exception.getCause().getMessage());

        verify(quoteApiClient, times(1)).fetchQuote();
    }
}
