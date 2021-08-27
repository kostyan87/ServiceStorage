package com.infotecs.servicestorage;

import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.service.StorageService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StorageServiceTests {

    @Autowired
    private StorageService storageService;

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
        storageService.set(new EntryDto("a", "aValue", 156));
        storageService.set(new EntryDto("d", "dValue", 34));
        storageService.set(new EntryDto("e", "eValue", 1426));
        storageService.set(new EntryDto("b", "bValue", 15));
        storageService.set(new EntryDto("c", "cValue", 25));

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
    void removeKeyThatIsNotInStorage() {
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.remove("not existing key");
        });
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(5)
    void removeKeyThatIsInStorage() throws NoDataException {
        String removeData = storageService.remove("a");
        Exception exception = assertThrows(NoDataException.class, () -> {
            storageService.get("a");
        });

        assertTrue(removeData.equals("aValue"));
        assertTrue(exception.getMessage().contains("No data"));
    }

    @Test
    @Order(6)
    void orderOfRecordsInTheStorage() {
        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
        assertEquals(order.get(0).getTtl(),15);
        assertEquals(order.get(1).getTtl(),25);
        assertEquals(order.get(2).getTtl(),34);
        assertEquals(order.get(3).getTtl(),1426);
    }

    @Test
    @Order(7)
    void setKeyThatIsAlreadyInStorage() throws NoDataException {
        EntryDto updateData = new EntryDto("b","new b value",68);
        storageService.set(updateData);
        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
        assertEquals(order.get(0).getTtl(),25);
        assertEquals(order.get(1).getTtl(),34);
        assertEquals(order.get(2).getTtl(),68);
        assertEquals(order.get(3).getTtl(),1426);
        assertTrue(storageService.get("b").equals("new b value"));
    }
}
