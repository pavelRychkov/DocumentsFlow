package com.etu.documentsflow.serviceImpl;

import com.etu.documentsflow.entity.RegCard;
import com.etu.documentsflow.repository.RegCardRepository;
import com.etu.documentsflow.service.RegCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RegCardServiceImpl implements RegCardService {
    @Autowired
    private RegCardRepository regCardRepository;

    @Override
    public void saveAndFlush(RegCard regCard) {
        regCardRepository.saveAndFlush(regCard);
    }

    @Override
    public RegCard findByDocumentId(Integer document_id) {
        return regCardRepository.findByDocumentId(document_id);
    }

    @Override
    public void remove(Timestamp timestamp, Integer externNum, Integer document_id) {
        regCardRepository.remove(timestamp,externNum,document_id);
    }

    @Override
    public Integer checkExterned(Integer document_id) {
        return regCardRepository.checkExterned(document_id);
    }
}
