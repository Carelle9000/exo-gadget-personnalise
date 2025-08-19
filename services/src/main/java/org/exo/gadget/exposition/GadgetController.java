package org.exo.gadget.exposition;

import org.exo.gadget.api.DocumentService;
import org.exo.gadget.api.QuoteService;
import org.exo.gadget.api.WeatherService;
import org.exo.gadget.model.GadgetData;
import org.exoplatform.services.security.ConversationState;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gadget")
public class GadgetController {
    @Inject private WeatherService weatherService;
    @Inject private QuoteService quoteService; 
    @Inject private DocumentService documentService;

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGadgetData(@QueryParam("lat") double lat, @QueryParam("lon") double lon) {
        String username = ConversationState.getCurrent().getIdentity().getUserId();
        String welcome = "Bienvenue, " + username + "!";
        WeatherData weather = weatherService.getWeather(lat, lon);
        Quote quote = quoteService.getDailyQuote();
        var documents = documentService.getRecentDocuments(username);
        GadgetData data = new GadgetData(welcome, weather, quote, documents);
        return Response.ok(data).build();
    }
}