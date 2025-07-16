package com.imagereader.image_reader_web.service;

import net.sourceforge.tess4j.ITesseract;
import org.springframework.stereotype.Service;

@Service
public class TesseractSetupService {

    private static final String datapath = "tessdata";
    private static final String language = "por";
    private static final String dpi = "300";

    /**
     * Sets the datapath, language and user_defined_dpi of a ITesseract object,
     * according to the static values defined on this class.
     * @param tesseract the ITesseract object to be configured
     */
    public static void setUpTesseract(ITesseract tesseract){
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
        tesseract.setVariable("user_defined_dpi", dpi);
    }

    public static void setUpTesseract(ITesseract tesseract, String datapath, String language){
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
        tesseract.setVariable("user_defined_dpi", dpi);
    }

    public static void setUpTesseract(ITesseract tesseract, String datapath, String language, String dpi){
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
        tesseract.setVariable("user_defined_dpi", dpi);
    }
}
