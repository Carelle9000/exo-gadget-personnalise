package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.common.RealtimeListAccess;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private ActivityManager activityManager;

    @Mock
    private IdentityManager identityManager;

    @Mock
    private ConversationState conversationState;

    @Mock
    private Identity userIdentity;

    @Mock
    private org.exoplatform.services.security.Identity securityIdentity;

    @Mock
    private ExoSocialActivity activity;

    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        // Initialise le service avec les mocks
        documentService = new DocumentServiceImpl(activityManager, identityManager);

        // Simule l'utilisateur connecté dans ConversationState
        when(securityIdentity.getUserId()).thenReturn("testUser");
        when(conversationState.getIdentity()).thenReturn(securityIdentity);
        ConversationState.setCurrent(conversationState);
    }

    @Test
    void testGetRecentDocumentsSuccess() throws Exception {
        // Préparation des données simulées
        String username = "testUser";
        String docTitle = "Document Test";
        long postedTime = 1697068800000L;

        @SuppressWarnings("unchecked")
        RealtimeListAccess<ExoSocialActivity> activityListAccess = mock(RealtimeListAccess.class);

        when(identityManager.getIdentity(username, true)).thenReturn(userIdentity);
        when(activityManager.getActivitiesWithListAccess(userIdentity)).thenReturn(activityListAccess);
        when(activityListAccess.loadAsList(0, 10)).thenReturn(Collections.singletonList(activity));

        when(activity.getType()).thenReturn("CONTENT");
        when(activity.getName()).thenReturn(docTitle);
        when(activity.getPostedTime()).thenReturn(postedTime);

        // Exécution
        List<Document> documents = documentService.getRecentDocuments(username);

        // Vérifications
        assertThat(documents).isNotNull().hasSize(1);
        Document result = documents.get(0);
        assertThat(result.getTitle()).isEqualTo(docTitle);
        assertThat(result.getDate()).isEqualTo(new Date(postedTime));

        // Vérifie interactions avec les mocks
        verify(identityManager).getIdentity(username, true);
        verify(activityManager).getActivitiesWithListAccess(userIdentity);
        verify(activityListAccess).loadAsList(0, 10);
    }

    @Test
    void testGetRecentDocumentsUnauthorized() {
        // Simule un utilisateur différent connecté
        when(securityIdentity.getUserId()).thenReturn("otherUser");
        ConversationState.setCurrent(conversationState);

        assertThrows(SecurityException.class, () ->
                documentService.getRecentDocuments("testUser"));
    }

    @Test
    void testGetRecentDocumentsEmptyActivities() throws Exception {
        String username = "testUser";

        @SuppressWarnings("unchecked")
        RealtimeListAccess<ExoSocialActivity> activityListAccess = mock(RealtimeListAccess.class);

        when(identityManager.getIdentity(username, true)).thenReturn(userIdentity);
        when(activityManager.getActivitiesWithListAccess(userIdentity)).thenReturn(activityListAccess);
        when(activityListAccess.loadAsList(0, 10)).thenReturn(Collections.emptyList());

        List<Document> documents = documentService.getRecentDocuments(username);
        assertThat(documents).isNotNull().isEmpty();
    }

    @Test
    void testGetRecentDocumentsNoIdentity() {
        String username = "testUser";
        when(identityManager.getIdentity(username, true)).thenReturn(null);

        List<Document> documents = documentService.getRecentDocuments(username);
        assertThat(documents).isNotNull().isEmpty();
    }

    @Test
    void testGetRecentDocumentsActivityListAccessNull() {
        String username = "testUser";
        when(identityManager.getIdentity(username, true)).thenReturn(userIdentity);
        when(activityManager.getActivitiesWithListAccess(userIdentity)).thenReturn(null);

        List<Document> documents = documentService.getRecentDocuments(username);
        assertThat(documents).isNotNull().isEmpty();
    }
}
