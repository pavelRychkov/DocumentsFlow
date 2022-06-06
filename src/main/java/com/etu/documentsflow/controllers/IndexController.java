package com.etu.documentsflow.controllers;

import com.etu.documentsflow.entity.Document;
import com.etu.documentsflow.serviceImpl.DocumentServiceImpl;
import com.etu.documentsflow.serviceImpl.RegCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private RegCardServiceImpl regCardService;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String provideUploadInfo(Model model) {
        List<Document> stillActive = new LinkedList<>();
        List<Document> notActive = new LinkedList<>();

        for(Document document: documentService.findAll()){
            if(!(regCardService.checkExterned(document.getDocument_id())>0)){
                stillActive.add(document);
            } else {
                notActive.add(document);
            }
        }

        model.addAttribute("activeDocuments", stillActive);
        model.addAttribute("inactiveDocuments", notActive);

        return "index";
    }
}
