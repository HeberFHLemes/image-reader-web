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

    /**
     * Takes a MultipartFile and tries to do an OCR operation with it, if it is a supported file type.
     * @param userFile the MultipartFile uploaded by the user
     * @return the OCR text output
     * @throws IOException
     * @throws TesseractException
     * @throws UnsupportedFileException
     */
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

    /**
     * Verifies if the type of the file is supported.
     * <p>Supported file types: {@code .png}, {@code .jpg}, {@code .jpeg}, {@code .tif}.</p>
     *
     * @param userFile the file uploaded
     * @return boolean
     */
    private boolean isSupportedFile(MultipartFile userFile){
        String lower = Objects.requireNonNull(userFile.getOriginalFilename()).toLowerCase();

        // Filtering .png, .jpg, .jpeg and .tif files
        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".tif");
    }

    /**
     * Creates a temporary file using another and returns it.
     * @param userFile the file to be used to create the temporary
     * @return the temporary file created
     * @throws IOException
     */
    private File createTempFile(MultipartFile userFile) throws IOException {
        String suffix = getSuffix(Objects.requireNonNull(userFile.getOriginalFilename()));
        return File.createTempFile("ocr_", suffix);
    }

    /**
     * Gets the suffix of the file (last one)
     * @param originalFilename the filename
     * @return the suffix (file extension)
     */
    private String getSuffix(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
}
