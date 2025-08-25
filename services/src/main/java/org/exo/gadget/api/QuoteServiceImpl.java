package org.exo.gadget.api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.exo.gadget.model.Quote;
import org.exo.gadget.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class QuoteServiceImpl implements QuoteService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CloseableHttpClient httpClient;

    // Constructeur pour injection (Mockito utilisera ce constructeur)
    public QuoteServiceImpl(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Quote getDailyQuote() {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(Constants.QUOTE_API_URL))) {
            String respString = EntityUtils.toString(response.getEntity());
            JsonNode root = mapper.readTree(respString);

            if (root.isEmpty() || root.get(0) == null || root.get(0).get("content") == null
                    || root.get(0).get("author") == null) {
                throw new RuntimeException("Erreur citation");
            }

            JsonNode json = root.get(0);
            return new Quote(json.get("content").asText(), json.get("author").asText());
        } catch (IOException e) {
            throw new RuntimeException("Erreur citation", e);
        }
    }
}
