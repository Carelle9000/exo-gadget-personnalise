package org.exo.gadget.api;

import java.util.List;
import org.exo.gadget.model.Document;

/**
 * Interface DocumentService
 * --------------------------
 * Cette interface définit le contrat pour tout service capable de récupérer des documents récents.

 * L'utilisation d'une interface permet :
 * 1. De séparer la définition du comportement (contrat) de son implémentation.
 * 2. De faciliter le testing (on peut mocker l'interface).
 * 3. De permettre différentes implémentations selon les sources de données (base de données, API, fichiers…).
 */
public interface DocumentService {

    /**
     * Récupère une liste des documents récents pour un utilisateur donné.
     *
     * @param username Nom de l'utilisateur connecté pour lequel on récupère les documents
     * @return une liste de Document contenant au maximum les documents récents
     *         (la quantité et le filtrage exact dépendent de l'implémentation).
     * @throws SecurityException si l'utilisateur n'est pas autorisé à accéder aux documents
     */
    List<Document> getRecentDocuments(String username);
}
