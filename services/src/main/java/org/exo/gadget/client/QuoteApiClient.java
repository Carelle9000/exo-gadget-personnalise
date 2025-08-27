package org.exo.gadget.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exo.gadget.model.Quote;
import org.exo.gadget.util.Constants;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * QuoteApiClient
 * ----------------
 * Cette classe est responsable de l'appel HTTP à l'API Quotable et du parsing JSON.
 * Elle retourne un objet Quote prêt à l'emploi.
 */
public class QuoteApiClient {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuoteApiClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Récupère une citation aléatoire depuis l'API Quotable
     *
     * @return un objet Quote avec le contenu et l'auteur
     * @throws IOException si un problème réseau survient
     */
    public Quote fetchQuote() throws IOException {
        HttpGet request = new HttpGet(Constants.QUOTE_API_URL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());

            // Parsing JSON
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (root.isEmpty() || root.get(0) == null
                    || root.get(0).get("content") == null
                    || root.get(0).get("author") == null) {
                throw new IOException("JSON invalide : données manquantes");
            }

            JsonNode json = root.get(0);
            return new Quote(json.get("content").asText(), json.get("author").asText());
        }
    }
}
