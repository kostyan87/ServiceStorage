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

    @Autowired
    private StorageService storageService;

    @Autowired
    private RemoveOnTimeService removeOnTimeService;

    @Test
    void removeOnTimeTest() throws InterruptedException {
        long time1 = System.currentTimeMillis() + 34;
        long time2 = System.currentTimeMillis() + 1426;

        storageService.set(new EntryDto("d", "dValue",
                time1));
        storageService.set(new EntryDto("e", "eValue",
                time2));
        storageService.set(new EntryDto("b", "bValue",
                System.currentTimeMillis() + 15));
        storageService.set(new EntryDto("c", "cValue",
                System.currentTimeMillis() + 25));

        Thread.sleep(26);

        List<EntryDto> order = new ArrayList<>(storageService.getDeleteSet());
//        Exception exception1 = assertThrows(NoDataException.class, () -> {
//            storageService.get("b");
//        });
//        Exception exception2 = assertThrows(NoDataException.class, () -> {
//            storageService.get("c");
//        });
//
//        assertTrue(exception1.getMessage().contains("No data"));
//        assertTrue(exception1.getMessage().contains("No data"));

        assertEquals(order.get(0).getTtl(), time1);
        assertEquals(order.get(1).getTtl(), time2);
    }
}
