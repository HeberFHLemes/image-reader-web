package com.imagereader.image_reader_web.service;

import net.sourceforge.tess4j.ITesseract;
import org.springframework.stereotype.Service;

@Service
public class TesseractSetupService {

    private static final String datapath = "tessdata";
    private static final String language = "por";

    public static void setUpTesseract(ITesseract tesseract){
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);

        tesseract.setVariable("user_defined_dpi", "300");
    }
}
