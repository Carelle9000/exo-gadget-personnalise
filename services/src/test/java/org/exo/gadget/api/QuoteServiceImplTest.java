package org.exo.gadget.api;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.exo.gadget.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteServiceImplTest {

    @Mock
    private CloseableHttpClient httpClient;
    @Mock
    private CloseableHttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;
    @Mock
    private StatusLine statusLine;
    @InjectMocks
    private QuoteServiceImpl quoteService;

    @BeforeEach
    void setUp() {
        quoteService = new QuoteServiceImpl(httpClient);
    }


    @Test
    void testGetDailyQuoteSuccess() throws Exception {
        String jsonResponse = "[{\"content\":\"Stay positive\",\"author\":\"Anonymous\"}]";

        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));

        Quote quote = quoteService.getDailyQuote();

        assertEquals("Stay positive", quote.getContent());
        assertEquals("Anonymous", quote.getAuthor());
        verify(httpClient).execute(any(HttpGet.class));
    }


    @Test
    void testGetDailyQuoteIOException() throws IOException {
        when(httpClient.execute(any(HttpUriRequest.class))).thenThrow(new IOException("Network error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> quoteService.getDailyQuote());
        assertEquals("Erreur citation", exception.getMessage());
        assertInstanceOf(IOException.class, exception.getCause());
        verify(httpClient).execute(any(HttpUriRequest.class));
    }

    @Test
    void testGetDailyQuoteInvalidJson() throws Exception {
        String invalidJson = "not a json";

        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(invalidJson.getBytes()));

        assertThrows(RuntimeException.class, () -> quoteService.getDailyQuote());
    }

    @Test
    void testGetDailyQuoteEmptyArray() throws Exception {
        // JSON simulé vide
        String jsonResponse = "[]";

        // Stubs nécessaires
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));

        // Exécution + Assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> quoteService.getDailyQuote());
        assertEquals("Erreur citation", exception.getMessage());

        verify(httpClient).execute(any(HttpGet.class));
    }



    @Test
    void testGetDailyQuoteMissingFields() throws IOException {
        String json = "[{\"otherField\":\"value\"}]";
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(stream);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> quoteService.getDailyQuote());
        assertEquals("Erreur citation", exception.getMessage());
        verify(httpClient).execute(any(HttpUriRequest.class));
        verify(httpResponse).close();
    }
}
