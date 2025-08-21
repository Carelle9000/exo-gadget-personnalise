package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private final ActivityManager activityManager;
    private final IdentityManager identityManager;

    // Constructeur pour l'injection des dépendances
    public DocumentServiceImpl(ActivityManager activityManager, IdentityManager identityManager) {
        this.activityManager = activityManager;
        this.identityManager = identityManager;
    }

    @Override
    public List<Document> getRecentDocuments(String username) {
        // Vérifier l'utilisateur connecté
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
        if (!currentUser.equals(username)) {
            throw new SecurityException("Accès non autorisé");
        }

        // Obtenir l'Identity de l'utilisateur
        Identity userIdentity = identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, username);
        if (userIdentity == null) {
            return Collections.emptyList();
        }

        // Récupérer les activités de l'utilisateur
        List<ExoSocialActivity> activities = activityManager.getActivities(userIdentity);

        // Filtrer les activités liées aux documents
        return activities.stream()
                .filter(a -> "CONTENT".equals(a.getType())) // Supposition : type "CONTENT" pour documents
                .limit(5) // Limiter à 5 documents
                .map(a -> {
                    // Supposition : utiliser getTitle() ou un champ alternatif
                    String docName = a.getName() != null ? a.getName(): "Document sans titre";
                    // Utiliser System.currentTimeMillis() comme fallback
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
                    return new Document(docName, date);
                })
                .collect(Collectors.toList());
    }
}