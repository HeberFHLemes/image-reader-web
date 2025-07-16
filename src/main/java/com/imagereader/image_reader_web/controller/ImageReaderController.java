package com.imagereader.image_reader_web.controller;

import com.imagereader.image_reader_web.service.ImageReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageReaderController {

    @Autowired
    ImageReaderService imageReaderService;

    @GetMapping("/")
    public String welcome(){

        // Show "welcome message" and user input options
        return "index";

    }

    private static final Logger logger = LoggerFactory.getLogger(ImageReaderController.class);

    /**
     * Upload image file via post method.
     * Calls the service that will process/validate the file,
     * adds the respective model attributes, and then returns the respective template.
     * @param file the MultipartFile uploaded by the user
     * @param model the Model to have the attributes updated
     * @return the updated template
     */
    @PostMapping("/upload")
    public String uploadImageFile(@RequestParam("file") MultipartFile file, Model model){

        if (file.isEmpty()){
            logger.warn("Upload attempt with empty file");
            model.addAttribute("ocrTextOutput", "Error: file is empty or no file was submitted.");
            model.addAttribute("message", "File not uploaded.");
            return "index";
        }

        // Receive Image File from user and send to the service that will process it
        try {
            // Calls the method that do the OCR operation after file validation
            String text = imageReaderService.processImageFile(file);

            model.addAttribute("ocrTextOutput", text);

            logger.info("User uploaded image file was processed successfully.");

        } catch (Exception e){
            logger.warn("Error processing user uploaded image file.");
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