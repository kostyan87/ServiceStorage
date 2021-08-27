package com.infotecs.servicestorage.service;

import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;

import java.util.Map;
import java.util.TreeSet;

public interface StorageService {

    String get(String key) throws NoDataException;

    void set(EntryDto entryDto);

    String remove(String key) throws NoDataException;

    Map<String, String> getAll();

    TreeSet<EntryDto> getDeleteSet();
}
