package com.infotecs.servicestorage.file;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

public interface FileManager {

    String parseFileToString(MultipartFile file) throws IOException;

    String getFileName();

    MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName);
}
