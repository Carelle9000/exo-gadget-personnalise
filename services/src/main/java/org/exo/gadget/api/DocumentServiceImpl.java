package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.common.RealtimeListAccess;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implémentation de DocumentService pour récupérer les documents récents d'un utilisateur.
 * <p>
 * Cette classe utilise les services ActivityManager et IdentityManager d'Exo Platform.
 */
public record DocumentServiceImpl(ActivityManager activityManager,
                                  IdentityManager identityManager) implements DocumentService {

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param activityManager pour accéder aux activités sociales
     * @param identityManager pour récupérer les identités utilisateurs
     */
    public DocumentServiceImpl {
    }

    /**
     * Récupère les documents récents pour un utilisateur donné.
     *
     * @param username nom d'utilisateur
     * @return liste de documents récents
     * @throws SecurityException si l'utilisateur connecté n'est pas le même username
     */
    @Override
    public List<Document> getRecentDocuments(String username) {
        // Vérifie que l'utilisateur connecté correspond à celui demandé
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
        if (!Objects.equals(currentUser, username)) {
            throw new SecurityException("Accès non autorisé");
        }

        // Récupère l'identité de l'utilisateur depuis IdentityManager
        Identity userIdentity = identityManager.getIdentity(username, true);
        if (userIdentity == null) {
            return Collections.emptyList(); // Aucun utilisateur trouvé retourne une liste vide
        }

        // Récupère la liste d'activités de l'utilisateur
        RealtimeListAccess<ExoSocialActivity> activityListAccess = activityManager.getActivitiesWithListAccess(userIdentity);
        if (activityListAccess == null) {
            return Collections.emptyList(); // Pas d'activités retourne liste vide
        }

        // Charge jusqu'à 10 activités de type "CONTENT"
        List<ExoSocialActivity> activities = activityListAccess.loadAsList(0, 10);

        // Filtre et map les activités vers des objets Document
        return activities.stream()
                .filter(a -> "CONTENT".equals(a.getType())) // Ne garde que les activités de type "CONTENT".
                .map(a -> new Document(
                        a.getName() != null ? a.getName() : "Document sans titre", // Si pas de titre, valeur par défaut
                        a.getPostedTime() != null ? new Date(a.getPostedTime()) : new Date() // Si pas de date, date actuelle
                ))
                .collect(Collectors.toList());
    }
}
