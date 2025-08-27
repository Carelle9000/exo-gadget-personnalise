package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.GadgetData;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;

import java.util.List;

/**
 * Contrôleur responsable de la composition des données du gadget.
 * <p>
 * Ce contrôleur récupère :
 * 1- Le message de bienvenue de l'utilisateur connecté
 * 2- La météo actuelle via WeatherService
 * 3- La citation du jour via QuoteService
 * 4- Les documents récents via DocumentService.
 */
public record GadgetController(WeatherService weatherService, QuoteService quoteService,
                               DocumentService documentService) {

    /**
     * Constructeur pour injection des services.
     */
    public GadgetController {
    }

    /**
     * Construit et retourne un objet GadgetData pour l'utilisateur connecté.
     *
     * @return GadgetData contenant toutes les informations à afficher
     */
    public GadgetData getGadgetData() {
        // Récupérer l'utilisateur connecté pour personnaliser le message
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
        String welcomeMessage = "Bonjour " + currentUser + "!";

        // Récupérer les données météo
        WeatherData weather = weatherService.getWeather();

        // Récupérer la citation du jour
        Quote quote = quoteService.getDailyQuote();

        // Récupérer les documents récents
        List<Document> documents = documentService.getRecentDocuments(currentUser);

        // Construire l'objet GadgetData
        return new GadgetData(welcomeMessage, weather, quote, documents);
    }
}
