package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.model.Document;
import org.exo.gadget.model.Quote;
import org.exo.gadget.model.WeatherData;
import org.exoplatform.services.security.ConversationState;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Service REST pour exposer chaque API individuellement.
 */
@Path("/gadget")
public class GadgetRestService {

    private final WeatherService weatherService;
    private final QuoteService quoteService;
    private final DocumentService documentService;

    @Inject
    public GadgetRestService(WeatherService weatherService, QuoteService quoteService,
                             DocumentService documentService) {
        this.weatherService = weatherService;
        this.quoteService = quoteService;
        this.documentService = documentService;
    }

    /**
     * Endpoint pour récupérer la météo actuelle.
     */
    @GET
    @Path("/weather")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeather() {
        try {
            WeatherData weather = weatherService.getWeather();
            return Response.ok(weather).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération de la météo : " + e.getMessage())
                    .build();
        }
    }

    /**
     * Endpoint pour récupérer la citation du jour.
     */
    @GET
    @Path("/quote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuote() {
        try {
            Quote quote = quoteService.getDailyQuote();
            return Response.ok(quote).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération de la citation : " + e.getMessage())
                    .build();
        }
    }

    /**
     * Endpoint pour récupérer les documents récents de l'utilisateur connecté.
     */
    @GET
    @Path("/documents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocuments() {
        try {
            String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
            List<Document> documents = documentService.getRecentDocuments(currentUser);
            return Response.ok(documents).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des documents : " + e.getMessage())
                    .build();
        }
    }
}
