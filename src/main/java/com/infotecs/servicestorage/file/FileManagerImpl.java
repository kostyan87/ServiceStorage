package com.infotecs.servicestorage.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FileManagerImpl implements FileManager {
    @Override
    public String parseFileToString(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
        String s = "";
        String buff;
        while ((buff = br.readLine()) != null) {
            s = s + buff/* + "\n"*/;
        }
        br.close();
        return s;
    }
}
