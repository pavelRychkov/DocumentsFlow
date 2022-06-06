package com.etu.documentsflow.controllers;

import com.etu.documentsflow.entity.Document;
import com.etu.documentsflow.entity.DocumentVersion;
import com.etu.documentsflow.entity.RegCard;
import com.etu.documentsflow.serviceImpl.DocumentServiceImpl;
import com.etu.documentsflow.serviceImpl.DocumentVersionServiceImpl;
import com.etu.documentsflow.serviceImpl.RegCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Timestamp;

@Controller
public class UploadPageController {

    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private DocumentVersionServiceImpl documentVersionService;
    @Autowired
    private RegCardServiceImpl regCardService;

    @GetMapping(value = "/uploadpage")
    public String uploadPageController( Model model){
        model.addAttribute("date", new Timestamp(System.currentTimeMillis()));
        return "uploadpage";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String handleFileUpload(@RequestParam("documentName") String name,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("documentIntroNumber") String introNum,
                                   @RequestParam("author") String author,
                                   RedirectAttributes redirectAttributes) {
        if (name.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Имя не может быть пустым!");
            return "redirect:/uploadpage";
        }
        if (introNum.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Входящий номер не может быть пустым!");
            return "redirect:/uploadpage";
        }
        if (author.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Автор не может быть пустым!");
            return "redirect:/uploadpage";
        }
        if (!file.isEmpty()) {
            Document newDoc = new Document();
            newDoc.setDocument_name(name);
            newDoc.setAuthor(author);
            documentService.saveAndFlush(newDoc);

            DocumentVersion newDocVersion = new DocumentVersion();
            newDocVersion.setDocument_version_id(1);
            newDocVersion.setDocument_id(newDoc.getDocument_id());
            newDocVersion.setVersion_author(author);
            try {
                newDocVersion.setContent(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            documentVersionService.saveAndFlush(newDocVersion);

            RegCard regCard = new RegCard();
            regCard.setDocumentId(newDoc.getDocument_id());
            regCard.setDocumentIntroNumber(introNum);
            regCard.setDateIntro(new Timestamp(System.currentTimeMillis()));

            regCardService.saveAndFlush(regCard);

        } else {
            redirectAttributes.addFlashAttribute("message", "Выберите файл!");
            return "redirect:/uploadpage";
        }
        redirectAttributes.addFlashAttribute("message", "Вы успешно загрузили файл " + name + "!");
        return "redirect:/uploadpage";
    }
}
