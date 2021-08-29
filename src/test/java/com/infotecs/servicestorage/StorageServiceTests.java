package com.infotecs.servicestorage;

import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.service.StorageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StorageServiceTests {

    @Autowired
    private StorageService storageService;

    void setFields(EntryDto entryDto,
                   String key, String value, long ttl) {
        entryDto.setKey(key);
        entryDto.setValue(value);
        entryDto.setTtl(ttl);
    }

    @Test
    @Order(1)
    void getWithEmptyStorageTest() {
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.get("a");
        });
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(2)
    void getAndSetWithFullStorageTest() throws NoDataException {
        EntryDto entryDto1 = new EntryDto();
        setFields(entryDto1,"a", "aValue", 156);
        EntryDto entryDto2 = new EntryDto();
        setFields(entryDto2,"d", "dValue", 34);
        EntryDto entryDto3 = new EntryDto();
        setFields(entryDto3,"e", "eValue", 1426);
        EntryDto entryDto4 = new EntryDto();
        setFields(entryDto4,"b", "bValue", 15);
        EntryDto entryDto5 = new EntryDto();
        setFields(entryDto5,"c", "cValue", 25);

        storageService.set(entryDto1);
        storageService.set(entryDto2);
        storageService.set(entryDto3);
        storageService.set(entryDto4);
        storageService.set(entryDto5);

        assertTrue(storageService.get("a").equals("aValue"));
        assertTrue(storageService.get("b").equals("bValue"));
        assertTrue(storageService.get("c").equals("cValue"));
        assertTrue(storageService.get("d").equals("dValue"));
        assertTrue(storageService.get("e").equals("eValue"));
    }

    @Test
    @Order(3)
    void getOnANonExistentKeyTest() {
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.get("not existing key");
        });
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(4)
    void removeKeyThatIsNotInStorageTest() {
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.remove("not existing key");
        });
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(5)
    void removeKeyThatIsInStorageTest() throws NoDataException {
        String removeData = storageService.remove("a");
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.get("a");
        });

        assertTrue(removeData.equals("aValue"));
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(6)
    void orderOfRecordsInTheStorageTest() {
        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
        assertEquals(order.get(0).getTtl(),System.currentTimeMillis() / 1000 + 15);
        assertEquals(order.get(1).getTtl(),System.currentTimeMillis() / 1000 + 25);
        assertEquals(order.get(2).getTtl(),System.currentTimeMillis() / 1000 + 34);
        assertEquals(order.get(3).getTtl(),System.currentTimeMillis() / 1000 + 1426);
    }

    @Test
    @Order(7)
    void setKeyThatIsAlreadyInStorageTest() throws NoDataException {
        EntryDto updateData = new EntryDto();
        setFields(updateData,"b","new b value",68);
        storageService.set(updateData);
        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
        assertEquals(order.get(0).getTtl(),System.currentTimeMillis() / 1000 + 25);
        assertEquals(order.get(1).getTtl(),System.currentTimeMillis() / 1000 + 34);
        assertEquals(order.get(2).getTtl(),System.currentTimeMillis() / 1000 + 68);
        assertEquals(order.get(3).getTtl(),System.currentTimeMillis() / 1000 + 1426);
        assertTrue(storageService.get("b").equals("new b value"));
    }

    @Test
    @Order(8)
    void loadStorageTest() throws IOException {
        MultipartFile multipartFile =
                new MockMultipartFile("test.json",
                        new FileInputStream(
                                new File("/home/kostya/Java/ServiceStorage/" +
                                        "src/test/java/com/infotecs/servicestorage",
                                        "test.json")));
        storageService.load(multipartFile);

        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
        assertEquals(order.get(0).getKey(),"b");
        assertEquals(order.get(1).getKey(),"d");
        assertEquals(order.get(2).getKey(),"c");
        assertEquals(order.get(3).getKey(),"a");
    }
}
