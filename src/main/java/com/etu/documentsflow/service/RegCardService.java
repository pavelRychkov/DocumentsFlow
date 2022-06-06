package com.etu.documentsflow.service;

import com.etu.documentsflow.entity.RegCard;

import java.sql.Timestamp;

public interface RegCardService {
    void saveAndFlush(RegCard regCard);
    RegCard findByDocumentId(Integer document_id);
    void remove(Timestamp timestamp, Integer externNum, Integer document_id);
    Integer checkExterned(Integer document_id);
}
