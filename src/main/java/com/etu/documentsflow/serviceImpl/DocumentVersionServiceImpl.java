package com.etu.documentsflow.serviceImpl;

import com.etu.documentsflow.entity.DocumentVersion;
import com.etu.documentsflow.repository.DocumentVersionRepository;
import com.etu.documentsflow.service.DocumentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Override
    public void saveAndFlush(DocumentVersion documentVersion) {
        documentVersionRepository.saveAndFlush(documentVersion);
    }

    @Override
    public DocumentVersion findDocumentVersionsByDocument_version_idAndDocument_id(Integer document_id, Integer document_version_id) {
        return documentVersionRepository.findDocumentVersionsByDocument_version_idAndDocument_id(document_id,document_version_id);
    }

    @Override
    public Integer findLatestVersion(Integer document_id) {
        return documentVersionRepository.findLatestVersion(document_id);
    }

    @Override
    public List<Integer> findAllVersions(Integer document_id) {
        return documentVersionRepository.findAllVersions(document_id);
    }
}
