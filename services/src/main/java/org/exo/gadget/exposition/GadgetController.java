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
 * Implémentation du contrôleur {@link GadgetControllerService} responsable
 * de la composition des données à afficher dans le gadget utilisateur.
 *
 * <p>
 *  Ce contrôleur s'appuie sur trois services principaux pour collecter
 *  les informations nécessaires :
 *  <ul>
 *     <li>{@link WeatherService} pour obtenir la météo actuelle</li>
 *     <li>{@link QuoteService} pour fournir la citation du jour</li>
 *     <li>{@link DocumentService} pour lister les documents récents
 *         liés à l'utilisateur connecté</li>
 *  </ul>
 *  </p>
 *
 * <p>
 * L'agrégation de ces informations est retournée sous la forme
 * d'un objet {@link GadgetData}.
 * </p>
 *
 * @author Ornella
 * @version 1.0
 */
public record GadgetController(WeatherService weatherService, QuoteService quoteService,
                               DocumentService documentService) implements GadgetControllerService {

    /**
     * Constructeur permettant l'injection des services nécessaires
     * au fonctionnement du contrôleur.
     *
     * @param weatherService  Service de récupération des données météo
     * @param quoteService    Service de récupération de la citation du jour
     * @param documentService Service de récupération des documents récents
     */
    public GadgetController {
    }

    /**
     * Construit et retourne un objet {@link GadgetData} contenant :
     * <ul>
     *     <li>Un message personnalisé pour l'utilisateur connecté</li>
     *     <li>Les informations météorologiques actuelles</li>
     *     <li>La citation du jour</li>
     *     <li>Les documents récents associés à l'utilisateur</li>
     * </ul>
     *
     * <p>
     * Le nom de l'utilisateur connecté est récupéré via
     * {@link ConversationState#getCurrent()}.
     * </p>
     *
     * @return un objet {@link GadgetData} regroupant toutes les informations
     * nécessaires à l'affichage du gadget.
     */
    @Override
    public GadgetData getGadgetData() {
        // Récupérer l'identifiant de l'utilisateur connecté
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();

        // Construire le message de bienvenue
        String welcomeMessage = "Bonjour " + currentUser + "!";

        // Récupérer les données météo
        WeatherData weather = weatherService.getWeather();

        // Récupérer la citation du jour
        Quote quote = quoteService.getDailyQuote();

        // Récupérer la liste des documents récents
        List<Document> documents = documentService.getRecentDocuments(currentUser);

        // Retourner l'objet GadgetData complet
        return new GadgetData(welcomeMessage, weather, quote, documents);
    }
}
