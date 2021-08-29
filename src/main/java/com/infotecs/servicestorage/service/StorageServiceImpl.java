package com.infotecs.servicestorage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotecs.servicestorage.dao.StorageDao;
import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.file.FileManager;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static java.util.Objects.isNull;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;

    @Autowired
    private FileManager fileManager;

    @Override
    public String get(String key) throws NoDataException {
        String data = storageDao.get(key);
        if (isNull(data))
            throw new NoDataException("No data");
        return storageDao.get(key);
    }

    @Override
    public void set(EntryDto entryDto) {
        storageDao.set(entryDto);
    }

    @Override
    public String remove(String key) throws NoDataException {
        String data = storageDao.remove(key);
        if (isNull(data))
            throw new NoDataException("No data");
        return data;
    }

    @Override
    public Map<String, String> getAll() {
        return storageDao.getData();
    }

    @Override
    public TreeSet<EntryDto> getDeleteSet() {
        return storageDao.getDeleteSet();
    }

    public void load(MultipartFile file) throws IOException {
        String s = fileManager.parseFileToString(file);
        ObjectMapper mapper = new ObjectMapper();
        EntryDto[] entryDtoList = mapper.readValue(s, EntryDto[].class);
        storageDao.getData().clear();
        storageDao.getDeleteSet().clear();
        for (EntryDto e:
             entryDtoList) {
            set(e);
        }
    }
}
