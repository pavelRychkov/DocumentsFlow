package com.etu.documentsflow.serviceImpl;

import com.etu.documentsflow.entity.Document;
import com.etu.documentsflow.repository.DocumentRepository;
import com.etu.documentsflow.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void saveAndFlush(Document document) {
        documentRepository.saveAndFlush(document);
    }

    @Override
    public Document findById(Integer id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
}
