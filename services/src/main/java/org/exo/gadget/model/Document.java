package org.exo.gadget.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Classe modèle représentant un document.
 * ----------------------------------------
 * Cette classe sert de DTO (Data Transfer Object) pour stocker les informations
 * d'un document dans l'application.

 * Elle contient deux attributs principaux :
 * 1- title : le titre du document
 * 2- date : la date de création ou de publication du document.

 * L'utilisation de Lombok (@Getter et @Setter) permet de générer automatiquement
 * les méthodes d'accès (getters et setters) pour les champs, évitant d'écrire
 * du code répétitif.
 */
@Getter
@Setter
public class Document {

    /**
     * Titre du document.
     * Exemple : "Rapport annuel 2025"
     */
    private String title;

    /**
     * Date associée au document (publication, création, etc.)
     */
    private Date date;

    /**
     * Constructeur paramétré pour initialiser le document avec un titre et une date.
     *
     * @param title Le titre du document
     * @param date  La date du document
     */
    public Document(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    // Lombok génère automatiquement les getters et setters
}
