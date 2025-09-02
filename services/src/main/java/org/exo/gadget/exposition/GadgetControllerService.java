package org.exo.gadget.exposition;

import org.exo.gadget.model.GadgetData;

/**
 * Interface représentant le contrôleur responsable de la construction
 * des données à afficher dans le gadget.
 *
 * <p>
 *  Cette interface définit le contrat permettant de :
 *  <ul>
 *     <li>Récupérer le message de bienvenue de l'utilisateur connecté</li>
 *     <li>Obtenir les informations météorologiques actuelles</li>
 *     <li>Fournir la citation du jour</li>
 *     <li>Récupérer la liste des documents récents</li>
 *  </ul>
 *  L'implémentation doit agréger ces différentes sources de données
 *  en un objet {@link GadgetData}.
 * </p>
 *
 * @author Ornella
 * @version 1.0
 */
public interface GadgetControllerService {

    /**
     * Construit et retourne les données complètes du gadget pour l'utilisateur connecté.
     *
     * <p>
     * L'objet retourné doit inclure :
     * <ul>
     *     <li>Un message personnalisé pour l'utilisateur courant</li>
     *     <li>Les données météo actuelles</li>
     *     <li>Une citation du jour</li>
     *     <li>Une liste des documents récents associés à l'utilisateur</li>
     * </ul>
     * </p>
     *
     * @return un objet {@link GadgetData} contenant l'ensemble des informations
     *         à afficher dans le gadget
     */
    GadgetData getGadgetData();
}
