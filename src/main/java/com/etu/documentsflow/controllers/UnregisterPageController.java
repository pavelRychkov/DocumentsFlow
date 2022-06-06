package com.etu.documentsflow.controllers;

import com.etu.documentsflow.serviceImpl.DocumentServiceImpl;
import com.etu.documentsflow.serviceImpl.RegCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;

@Controller
public class UnregisterPageController {
    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private RegCardServiceImpl regCardService;

    @GetMapping(value = "/unregister/{document_id}")
    public String removePageController(@PathVariable("document_id") Integer document_id,
                                       Model model
    ){
        model.addAttribute("date", new Timestamp(System.currentTimeMillis()));
        model.addAttribute("documentName", documentService.findById(document_id).getDocument_name());
        return "unregisterpage";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/remove/{document_id}")
    public String removeController(@PathVariable("document_id") Integer document_id,
                                   @RequestParam("documentExternNumber") String documentExternNumber,
                                   RedirectAttributes redirectAttributes){
        if (documentExternNumber.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Исходящий номер не может быть пустым!");
            return "redirect:/unregister/{document_id}";
        }
        regCardService.remove(new Timestamp(System.currentTimeMillis()), Integer.valueOf(documentExternNumber), document_id);
        redirectAttributes.addFlashAttribute("message", "Вы успешно сняли файл с учета!");
        return "redirect:/unregister/{document_id}";
    }
}
