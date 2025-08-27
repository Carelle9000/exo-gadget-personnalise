package org.exo.gadget.api;

import org.exo.gadget.client.QuoteApiClient;
import org.exo.gadget.model.Quote;

/**
 * Implémentation de l'interface QuoteService
 * ------------------------------------------
 * Cette classe utilise QuoteApiClient pour récupérer la citation.
 * Elle se concentre uniquement sur la logique métier et la gestion d'erreurs.
 */
public record QuoteServiceImpl(QuoteApiClient quoteApiClient) implements QuoteService {

    /**
     * Récupère la citation du jour.
     *
     * @return un objet Quote contenant le contenu et l'auteur
     * @throws RuntimeException si un problème survient lors de l'appel ou du parsing
     */
    @Override
    public Quote getDailyQuote() {
        try {
            return quoteApiClient.fetchQuote();
        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération citation", e);
        }
    }
}
