package org.exo.gadget.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.exo.gadget.model.Quote;
import org.exo.gadget.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class QuoteServiceImpl implements QuoteService {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Quote getDailyQuote() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(Constants.QUOTE_API_URL);
            String response = EntityUtils.toString(client.execute(request).getEntity());
            JsonNode json = mapper.readTree(response).get(0); // quotable.io retourne array
            return new Quote(json.get("content").asText(), json.get("author").asText());
        } catch (IOException e) {
            throw new RuntimeException("Erreur citation", e);
        }
    }
}