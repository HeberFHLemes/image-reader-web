package com.imagereader.image_reader_web.service;

import com.imagereader.image_reader_web.exception.UnsupportedFileException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class ImageReaderService {

    private ITesseract tesseract;

    public ImageReaderService(){
        this.tesseract = new Tesseract();
        TesseractSetupService.setUpTesseract(tesseract);
    }

    public String processImageFile(MultipartFile userFile) throws IOException, TesseractException, UnsupportedFileException {

        if (!isSupportedFile(userFile)){
            throw new UnsupportedFileException("File must be either '.png', '.jpg', '.jpeg' or '.tif'");
        }

        // Will do ocr with the temporary file (from a MultipartFile)
        File tempFile = createTempFile(userFile);

        userFile.transferTo(tempFile);

        // Calls OCR method
        String result = tesseract.doOCR(tempFile);

        // Deletes the temporary file used
        tempFile.delete();

        return result;
    }

    private boolean isSupportedFile(MultipartFile userFile){
        String lower = Objects.requireNonNull(userFile.getOriginalFilename()).toLowerCase();

        // Filtering .png, .jpg, .jpeg and .tif files
        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".tif");
    }

    private File createTempFile(MultipartFile userFile) throws IOException {
        String suffix = getSuffix(Objects.requireNonNull(userFile.getOriginalFilename()));
        return File.createTempFile("ocr_", suffix);
    }

    private String getSuffix(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
}
