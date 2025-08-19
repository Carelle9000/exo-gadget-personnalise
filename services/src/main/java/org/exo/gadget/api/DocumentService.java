package org.exo.gadget.api;

import java.util.List;
import org.exo.gadget.model.Document;

public interface DocumentService {
    List<Document> getRecentDocuments(String username);
}