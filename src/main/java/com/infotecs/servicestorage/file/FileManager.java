package com.infotecs.servicestorage.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {

    String parseFileToString(MultipartFile file) throws IOException;
}
