package com.team17.bikeworld.controller;

import com.team17.bikeworld.model.ResponseImage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.team17.bikeworld.common.CoreConstant.rootLocation;

@RestController
public class ImageController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/image/upload")
    public String upload(@RequestPart  MultipartFile upload, HttpServletRequest request) {

        String sourceName = upload.getOriginalFilename();
        String sourceFileName = FilenameUtils.getBaseName(sourceName);
        String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

        String fileName = RandomStringUtils.randomAlphabetic(8)
                .concat(sourceFileName)
                .concat(".")
                .concat(sourceExt);
        try {
            Files.createDirectories(rootLocation);
            Files.copy(upload.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = request.getScheme()
                .concat("://")
                .concat(request.getServerName())
                .concat(":8080")
                .concat("/images/")
                .concat(fileName);
        return gson.toJson(new ResponseImage(1, fileName, url));
    }


    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Resource resource = loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //load hinh
    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }
}
