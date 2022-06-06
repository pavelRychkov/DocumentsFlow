package com.etu.documentsflow.controllers;

import com.etu.documentsflow.entity.DocumentVersion;
import com.etu.documentsflow.entity.RegCard;
import com.etu.documentsflow.serviceImpl.DocumentServiceImpl;
import com.etu.documentsflow.serviceImpl.DocumentVersionServiceImpl;
import com.etu.documentsflow.serviceImpl.RegCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Controller
public class DownloadPageController {
    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private DocumentVersionServiceImpl documentVersionService;
    @Autowired
    private RegCardServiceImpl regCardService;

    @GetMapping(value = "/downloadpage/{document_id}")
    public String downloadPageController(@PathVariable("document_id") Integer document_id, Model model){
        RegCard regCard = regCardService.findByDocumentId(document_id);
        model.addAttribute("document_id", document_id);
        model.addAttribute("date", regCard.getDateIntro());
        model.addAttribute("documentName", documentService.findById(document_id).getDocument_name());
        model.addAttribute("documentIntroNumber",regCard.getDocumentIntroNumber());
        model.addAttribute("listOfVersions", documentVersionService.findAllVersions(document_id));
        return "downloadpage";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/download/{document_id}")
    public String downloadController(@PathVariable("document_id") Integer document_id,
                                     @RequestParam("document_version") Integer document_version,
                                     RedirectAttributes redirectAttributes){
        DocumentVersion file = documentVersionService.findDocumentVersionsByDocument_version_idAndDocument_id(document_id,document_version);
        String home = System.getProperty("user.home");
        File downloadedFile = new File(home+"/Downloads/" + documentService.findById(document_id).getDocument_name() + "_" + document_version + ".txt");
        writeByte(file.getContent(),downloadedFile);
        redirectAttributes.addFlashAttribute("message", "Файл "+downloadedFile.getName()+" успешно загружен и находится в папке Downloads!");
        return "redirect:/downloadpage/{document_id}";

    }

    static void writeByte(byte[] bytes, File file)
    {
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
