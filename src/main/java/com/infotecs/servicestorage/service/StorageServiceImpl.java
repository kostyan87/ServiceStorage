package com.infotecs.servicestorage.service;

import com.infotecs.servicestorage.dao.StorageDao;
import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeSet;

import static java.util.Objects.isNull;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;

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
}
