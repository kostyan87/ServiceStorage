package com.infotecs.servicestorage.controller;

import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeSet;

@RestController
@RequestMapping("/storage")
public class ServiceController {

    @Autowired
    public StorageService storageService;

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

    // Test methods

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
