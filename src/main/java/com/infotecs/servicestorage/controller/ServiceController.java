package com.infotecs.servicestorage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.file.FileManager;
import com.infotecs.servicestorage.file.FileManagerImpl;
import com.infotecs.servicestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.*;
import java.util.Map;
import java.util.TreeSet;

@RestController
@RequestMapping("/storage")
public class ServiceController {

    @Autowired
    public StorageService storageService;

    @Autowired
    public ServletContext servletContext;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String get(@PathVariable String key) throws NoDataException {
        return storageService.get(key);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String set(@RequestBody @Valid EntryDto entryDto) {
        storageService.set(entryDto);
        return "Data added to storage";
    }

    @RequestMapping(value = "/remove/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String remove(@PathVariable String key) throws NoDataException {
        return storageService.remove(key);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void load(@RequestParam("file") MultipartFile file) throws IOException {
        storageService.load(file);
    }

    @RequestMapping(value = "/dump", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> dump() throws JsonProcessingException {
        FileManager fileManager = new FileManagerImpl();
        String fileName = fileManager.getFileName();
        MediaType mediaType =
                fileManager.getMediaTypeForFileName(this.servletContext, fileName);
        byte[] data = storageService.dump().getBytes();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + fileManager.getFileName())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(resource);
    }

    // Other methods

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getAll() {
        return storageService.getAll();
    }

    @RequestMapping(value = "/deleteSet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TreeSet<EntryDto> getDeleteSet() {
        return storageService.getDeleteSet();
    }
}
