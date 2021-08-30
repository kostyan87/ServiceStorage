package com.infotecs.servicestorage.dao;

import com.infotecs.servicestorage.dto.EntryDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Repository
public class StorageDaoImpl implements StorageDao {

    private final Map<String, String> data;

    private final TreeSet<EntryDto> deleteSet;

    {
        data = new HashMap<>();
        deleteSet = new TreeSet<>();
    }

    // Getters

    @Override
    public Map<String, String> getData() {
        return data;
    }

    @Override
    public TreeSet<EntryDto> dump() {
        return deleteSet;
    }

    // StorageDao methods

    @Override
    public String get(String key) {
        return data.get(key);
    }

    @Override
    public void set(EntryDto entryDto) {
        data.put(entryDto.getKey(), entryDto.getValue());
        removeFromDeleteSet(entryDto.getKey());
        deleteSet.add(entryDto);
    }

    @Override
    public String remove(String key) {
        removeFromDeleteSet(key);
        return data.remove(key);
    }

    public void removeFromDeleteSet(String key) {
        deleteSet.removeIf((e) -> e.getKey().equals(key));
    }

    // Methods for test

}
