package org.exo.gadget.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Classe représentant l'ensemble des données affichées par le gadget.

 * Contient :
 * 1- le message de bienvenue
 * 2- la météo actuelle
 * 3- une citation du jour
 * 4- une liste de documents récents

 * Cette classe est utilisée pour regrouper toutes les informations à envoyer
 * au frontend.
 */
@Getter
public class GadgetData {

    // Message de bienvenue personnalisé


    private final String welcomeMessage;

    // Date et heure actuelles (géré côté frontend)

    @Setter
    private String dateTime;

    // Données météo

    private final WeatherData weather;

    // Citation du jour

    private final Quote quote;

    // Liste des documents récents

    private final List<Document> documents;

    /**
     * Constructeur principal pour initialiser toutes les données du gadget.
     *
     * @param welcomeMessage Message de bienvenue
     * @param weather Données météo
     * @param quote Citation du jour
     * @param documents Liste de documents récents
     */
    public GadgetData(String welcomeMessage, WeatherData weather, Quote quote, List<Document> documents) {
        this.welcomeMessage = welcomeMessage;
        this.weather = weather;
        this.quote = quote;
        this.documents = documents;
    }



}
