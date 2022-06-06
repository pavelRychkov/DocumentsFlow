package com.etu.documentsflow.service;


import com.etu.documentsflow.entity.DocumentVersion;

import java.util.List;

public interface DocumentVersionService {
    void saveAndFlush(DocumentVersion documentVersion);
    DocumentVersion findDocumentVersionsByDocument_version_idAndDocument_id(Integer document_id, Integer document_version_id);
    Integer findLatestVersion(Integer document_id);
    List<Integer> findAllVersions(Integer document_id);
}
