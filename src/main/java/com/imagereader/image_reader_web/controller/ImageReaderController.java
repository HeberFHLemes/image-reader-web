package com.imagereader.image_reader_web.controller;

import com.imagereader.image_reader_web.service.ImageReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ImageReaderController {

    @Autowired
    ImageReaderService imageReaderService;

    @GetMapping
    public String welcome(Model model){

        // Show "welcome message" and user input options
        return "index";

    }

    @PostMapping
    public String sendImageFile(@RequestParam("file") MultipartFile file, Model model){

        // Receive Image File from user and send to the service that will process it
        try{
            String text = imageReaderService.processImageFile(file);

            model.addAttribute("ocrTextOutput", text);

        } catch (Exception e){
            model.addAttribute("ocrTextOutput", "Error: " + e.getMessage());
        }

        // return text read from image (here or in another method).
        model.addAttribute("message", "File uploaded: " + file.getOriginalFilename());

        return "index";
    }
}
