package com.etu.documentsflow.service;

import com.etu.documentsflow.entity.Document;

import java.util.List;

public interface DocumentService {
    void saveAndFlush(Document document);
    Document findById(Integer id);
    List<Document> findAll();
}
