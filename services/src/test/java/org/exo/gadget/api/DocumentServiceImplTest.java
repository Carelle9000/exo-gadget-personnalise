package org.exo.gadget.api;

import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exo.gadget.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        when(identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, "testUser")).thenReturn(userIdentity);
        // Mock Activity
        when(activity.getType()).thenReturn("CONTENT");
        when(activity.getName()).thenReturn("Test Document");
        when(activityManager.getActivities(userIdentity)).thenReturn(Collections.singletonList(activity));

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals("Test Document", documents.get(0).getTitle());
    }

    @Test
    void testGetRecentDocumentsNoDocs() {
        // Mock Identity
        when(identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, "testUser")).thenReturn(userIdentity);
        // Mock Activity (liste vide)
        when(activityManager.getActivities(userIdentity)).thenReturn(Collections.emptyList());

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }

    @Test
    void testGetRecentDocumentsNoContentActivities() {
        // Mock Identity
        when(identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, "testUser")).thenReturn(userIdentity);
        // Mock Activity (type non CONTENT)
        when(activity.getType()).thenReturn("OTHER");
        when(activityManager.getActivities(userIdentity)).thenReturn(Collections.singletonList(activity));

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
        // Mock Identity (null)
        when(identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, "testUser")).thenReturn(null);

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }

    @Test
    void testGetRecentDocumentsNullName() {
        // Mock Identity
        when(identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, "testUser")).thenReturn(userIdentity);
        // Mock Activity (name null)
        when(activity.getType()).thenReturn("CONTENT");
        when(activity.getName()).thenReturn(null);
        when(activityManager.getActivities(userIdentity)).thenReturn(Collections.singletonList(activity));

        List<Document> documents = documentService.getRecentDocuments("testUser");

        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals("Document sans titre", documents.get(0).getTitle());
    }
}