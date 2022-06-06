package com.etu.documentsflow.controllers;

import com.etu.documentsflow.entity.DocumentVersion;
import com.etu.documentsflow.serviceImpl.DocumentServiceImpl;
import com.etu.documentsflow.serviceImpl.DocumentVersionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Timestamp;

@Controller
public class UploadVersionController {
    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private DocumentVersionServiceImpl documentVersionService;

    @GetMapping(value = "/uploadversion/{document_id}")
    public String newVersionController(@PathVariable("document_id") Integer document_id, Model model){
        model.addAttribute("date", new Timestamp(System.currentTimeMillis()));
        model.addAttribute("document_name", documentService.findById(document_id).getDocument_name());
        model.addAttribute("document_id", document_id);

        return "uploadversionpage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadVersion/{document_id}")
    public String newVersionUploadController(@PathVariable("document_id") Integer document_id,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("author") String author,
                                             RedirectAttributes redirectAttributes){
        if (author.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Автор не может быть пустым!");
            return "redirect:/uploadversion/{document_id}";
        }
        if (!file.isEmpty()) {
            DocumentVersion newDocVersion = new DocumentVersion();
            newDocVersion.setDocument_version_id(documentVersionService.findLatestVersion(document_id)+1);
            newDocVersion.setDocument_id(document_id);
            newDocVersion.setVersion_author(author);
            try {
                newDocVersion.setContent(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            documentVersionService.saveAndFlush(newDocVersion);
            redirectAttributes.addFlashAttribute("message", "Вы успешно загрузили файл!");
            return "redirect:/uploadversion/{document_id}";

        } else {
            redirectAttributes.addFlashAttribute("message", "Выберите файл!");
            return "redirect:/uploadversion/{document_id}";
        }
    }
}
