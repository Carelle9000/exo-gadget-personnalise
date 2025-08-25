package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

public record DocumentServiceImpl(ActivityManager activityManager,
                                  IdentityManager identityManager) implements DocumentService {

    @Override
    public List<Document> getRecentDocuments(String username) {
        // Vérifier l'utilisateur connecté
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
        if (!currentUser.equals(username)) {
            throw new SecurityException("Accès non autorisé");
        }

        // Obtenir l'Identity de l'utilisateur
        Identity userIdentity = identityManager.getIdentity(username, true);
        if (userIdentity == null) {
            return Collections.emptyList();
        }

        // Récupérer les activités de l'utilisateur avec pagination
        List<ExoSocialActivity> activities = activityManager.getActivitiesWithListAccess(userIdentity).loadAsList(0, 20);
        if (!activities.isEmpty()) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).info("Methods: " + Arrays.toString(activities.get(0).getClass().getMethods()));
        }

        // Formater la date en UTC
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Filtrer les activités liées aux documents
        return activities.stream()
                .filter(a -> "CONTENT".equals(a.getType()))
                .limit(5)
                .map(a -> {
                    String docName = a.getTitle() != null ? a.getTitle() : "Document sans titre";
                    String date = a.getPostedTime() != null
                            ? sdf.format(a.getPostedTime())
                            : sdf.format(System.currentTimeMillis());
                    return new Document(docName, date);
                })
                .toList();
    }
}