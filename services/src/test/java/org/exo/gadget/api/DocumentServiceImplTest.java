package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.common.RealtimeListAccess;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import  org.mockito.junit.jupiter.MockitoSettings;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private ActivityManager activityManager;
    @Mock
    private IdentityManager identityManager;
    @Mock
    private ConversationState conversationState;
    @Mock
    private Identity userIdentity; // org.exoplatform.social.core.identity.model.Identity
    @Mock
    private org.exoplatform.services.security.Identity securityIdentity;
    @Mock
    private ExoSocialActivity activity;
    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        // Initialiser DocumentServiceImpl avec les mocks
        documentService = new DocumentServiceImpl(activityManager, identityManager);
        // Configurer ConversationState
        when(securityIdentity.getUserId()).thenReturn("testUser");
        when(conversationState.getIdentity()).thenReturn(securityIdentity);
        ConversationState.setCurrent(conversationState);
    }



    @Test
    void testGetRecentDocumentsSuccess() {
        // Mock Identity
        when(identityManager.getIdentity("testUser", true)).thenReturn(userIdentity);
        // Mock Activity
        when(activity.getType()).thenReturn("CONTENT");
        when(activity.getTitle()).thenReturn("Test Document"); // Changement de getName à getTitle
        when(activity.getPostedTime()).thenReturn(1697059200000L); // Timestamp pour 2023-10-12 00:00:00
        when(activityManager.getActivitiesWithListAccess(userIdentity))
                .thenReturn(mock(RealtimeListAccess.class));
        when(activityManager.getActivitiesWithListAccess(userIdentity).loadAsList(0, 20))
                .thenReturn(Collections.singletonList(activity));

        List<Document> documents = documentService.getRecentDocuments("testUser");

        // Verify mock interactions
        verify(identityManager).getIdentity("testUser", true);
        verify(activityManager).getActivitiesWithListAccess(userIdentity);
        verify(activity).getType();
        verify(activity).getTitle();

        assertNotNull(documents);
        assertThat(documents).hasSize(1);
        assertThat(documents).extracting(Document::getTitle).containsExactly("Test Document");
        assertThat(documents).extracting(Document::getDate).containsExactly("2023-10-12 00:00:00");
    }

    @Test
    void testGetRecentDocumentsNoDocs() {
        when(identityManager.getIdentity("testUser", true)).thenReturn(userIdentity);
        when(activityManager.getActivitiesWithListAccess(userIdentity))
                .thenReturn(mock(RealtimeListAccess.class));
        when(activityManager.getActivitiesWithListAccess(userIdentity).loadAsList(0, 20))
                .thenReturn(Collections.emptyList());

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }

    @Test
    void testGetRecentDocumentsNoContentActivities() {
        when(identityManager.getIdentity("testUser", true)).thenReturn(userIdentity);
        when(activity.getType()).thenReturn("OTHER");
        when(activityManager.getActivitiesWithListAccess(userIdentity))
                .thenReturn(mock(RealtimeListAccess.class));
        when(activityManager.getActivitiesWithListAccess(userIdentity).loadAsList(0, 20))
                .thenReturn(Collections.singletonList(activity));

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }

    @Test
    void testGetRecentDocumentsUnauthorized() {
        // Simuler un utilisateur différent
        when(securityIdentity.getUserId()).thenReturn("otherUser");
        when(conversationState.getIdentity()).thenReturn(securityIdentity);
        ConversationState.setCurrent(conversationState);

        assertThrows(SecurityException.class, () -> documentService.getRecentDocuments("testUser"));
    }

    @Test
    void testGetRecentDocumentsNoIdentity() {
        when(identityManager.getIdentity("testUser", true)).thenReturn(null);

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }

    @Test
    void testGetRecentDocumentsNullName() {
        when(identityManager.getIdentity("testUser", true)).thenReturn(userIdentity);
        when(activity.getType()).thenReturn("CONTENT");
        when(activity.getTitle()).thenReturn(null);
        when(activity.getPostedTime()).thenReturn(1697059200000L); // 2023-10-12 00:00:00 UTC
        when(activityManager.getActivitiesWithListAccess(userIdentity))
                .thenReturn(mock(RealtimeListAccess.class));
        when(activityManager.getActivitiesWithListAccess(userIdentity).loadAsList(0, 20))
                .thenReturn(Collections.singletonList(activity));

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals("Document sans titre", documents.get(0).getTitle());
        assertEquals("2023-10-12 00:00:00", documents.get(0).getDate());
    }
}