package org.exo.gadget.api;

import org.exo.gadget.model.Quote;

/**
 * Interface QuoteService.
 * Cette interface définit le contrat pour tout service capable de fournir une citation quotidienne.
 * Elle est utilisée pour respecter le principe de programmation orientée interface (SOLID - DIP),
 * ce qui permet d'avoir plusieurs implémentations (ex. via API, via base de données, etc.)
 * sans changer le code qui dépend de cette interface.
 */
public interface QuoteService {

    /**
     * Méthode destinée à récupérer une citation du jour.
     *
     * @return un objet {@link Quote} représentant la citation (contenu et auteur).
     */
    Quote getDailyQuote();
}
