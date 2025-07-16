package com.imagereader.image_reader_web.controller;

import com.imagereader.image_reader_web.service.ImageReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageReaderController {

    @Autowired
    ImageReaderService imageReaderService;

    @GetMapping("/")
    public String welcome(Model model){

        // Show "welcome message" and user input options
        return "index";

    }

    /**
     * Upload image file via post method.
     * Calls the service that will process/validate the file,
     * adds the respective model attributes, and then returns the respective template.
     * @param file the MultipartFile uploaded by the user
     * @param model the Model to have the attributes updated
     * @return the updated template
     */
    @PostMapping("/upload")
    public String sendImageFile(@RequestParam("file") MultipartFile file, Model model){

        // Receive Image File from user and send to the service that will process it
        try {
            // Calls the method that do the OCR operation after file validation
            String text = imageReaderService.processImageFile(file);

            model.addAttribute("ocrTextOutput", text);

        } catch (Exception e){
            model.addAttribute("ocrTextOutput", "Error: " + e.getMessage());
        }

        // adds an information for the user - what file was submitted
        model.addAttribute("message", "File uploaded: " + file.getOriginalFilename());

        return "index";
    }

    /**
     * Helper mapping - refreshes the page redirecting to "/",
     * useful after file submission.
     * @return redirect to root
     */
    @PostMapping("/refresh")
    public String refreshPage(){
        return "redirect:/";
    }
}
