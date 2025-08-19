package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exo.gadget.model.Document;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    @Inject
    private ActivityManager activityManager;

    @Override
    public List<Document> getRecentDocuments(String username) {
        String currentUser = ConversationState.getCurrent().getIdentity().getUserId();
        if (!currentUser.equals(username)) {
            throw new SecurityException("Accès non autorisé");
        }
        List<ExoSocialActivity> activities = activityManager.getActivitiesOfUserSpacesWithOffset(currentUser, 0, 5); // Derniers 5
        return activities.stream()
                .filter(a -> a.getType().contains("DOC")) // Filtre documents
                .map(a -> new Document(a.getTitle(), a.getUpdated().toString()))
                .collect(Collectors.toList());
    }
}