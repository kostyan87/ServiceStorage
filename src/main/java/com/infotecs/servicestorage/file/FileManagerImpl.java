package com.infotecs.servicestorage.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
            s = s + buff;
        }
        br.close();
        return s;
    }

    @Override
    public String getFileName() {
        return "dump-" + new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss")
                .format(Calendar.getInstance().getTime()) + ".json";
    }

    @Override
    public MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
