# Image Reader Web Project

## Extract text from images with Tess4J OCR

### Usage
Upload a file through the form and the text read, if any, will be output below it.

### Language Support
Currently using **Portuguese**, if you want to use another language, download from the tessdata official repository, add the file to `tessdata/` and change `TesseractOutputService` to use it as needed.

### Used libraries and modules
- Tess4J
- slf4j (for Tess4J)
- Spring Boot
- Spring Web
- Thymeleaf
- Maven
