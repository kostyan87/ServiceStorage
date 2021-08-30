package com.infotecs.servicestorage.dao;

import com.infotecs.servicestorage.dto.EntryDto;

import java.util.Map;
import java.util.TreeSet;

public interface StorageDao {

    Map<String, String> getData();

    TreeSet<EntryDto> dump();

    String get(String key);

    void set(EntryDto entryDto);

    String remove(String key);
}
