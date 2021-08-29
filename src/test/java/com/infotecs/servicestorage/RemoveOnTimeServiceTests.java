package com.infotecs.servicestorage;

import com.infotecs.servicestorage.dto.EntryDto;
import com.infotecs.servicestorage.exceptions.NoDataException;
import com.infotecs.servicestorage.service.RemoveOnTimeService;
import com.infotecs.servicestorage.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RemoveOnTimeServiceTests {

//    @Autowired
//    private StorageService storageService;
//
//    @Autowired
//    private RemoveOnTimeService removeOnTimeService;
//
//    void setFields(EntryDto entryDto,
//                   String key, String value, long ttl) {
//        entryDto.setKey(key);
//        entryDto.setValue(value);
//        entryDto.setTtl(ttl);
//    }
//
//    @Test
//    void removeOnTimeTest() throws InterruptedException {
//        long time1 = System.currentTimeMillis() / 1000 + 34;
//        long time2 = System.currentTimeMillis() / 1000 + 1426;
//        EntryDto entryDto1 = new EntryDto();
//        setFields(entryDto1,"d", "dValue", time1);
//        EntryDto entryDto2 = new EntryDto();
//        setFields(entryDto2,"e", "eValue", time2);
//        EntryDto entryDto3 = new EntryDto();
//        setFields(entryDto3,"b", "bValue",System.currentTimeMillis() / 1000 + 5);
//        EntryDto entryDto4 = new EntryDto();
//        setFields(entryDto4,"c", "cValue",System.currentTimeMillis() / 1000 + 6);
//
//        storageService.set(entryDto1);
//        storageService.set(entryDto2);
//        storageService.set(entryDto3);
//        storageService.set(entryDto4);
//
//        Thread.sleep(7000);
//
//        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
//        Exception exception1 = assertThrows(NoDataException.class, () -> {
//            storageService.get("b");
//        });
//        Exception exception2 = assertThrows(NoDataException.class, () -> {
//            storageService.get("c");
//        });
//
//        assertTrue(exception1.getMessage().contains("No data"));
//        assertTrue(exception2.getMessage().contains("No data"));
//
//        assertEquals(order.get(0).getTtl(), time1);
//        assertEquals(order.get(1).getTtl(), time2);
//    }
}
