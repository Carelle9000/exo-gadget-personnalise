package org.exo.gadget.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe modèle représentant une citation.
 * Elle contient deux attributs principaux : le contenu de la citation (content)
 * et l'auteur de la citation (author).
 * Grâce à Lombok, les annotations @Getter et @Setter génèrent automatiquement
 * les méthodes get et set pour les champs, évitant ainsi d'écrire du code répétitif.
 */
@Setter
@Getter
public class Quote {

    /**
     * Texte de la citation.
     * Exemple : "La vie est belle."
     */
    private String content;

    /**
     * Auteur de la citation.
     * Exemple : "Albert Einstein".
     */
    private String author;

    /**
     * Constructeur paramétré permettant de créer un objet Quote avec son contenu et son auteur.
     * @param content Contenu de la citation.
     * @param author Auteur de la citation.
     */
    public Quote(String content, String author) {
        this.content = content;
        this.author = author;
    }

}
