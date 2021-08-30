package com.infotecs.servicestorage.service;

import com.infotecs.servicestorage.dao.StorageDao;
import com.infotecs.servicestorage.dto.EntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RemoveOnTimeServiceImpl implements RemoveOnTimeService {

    @Autowired
    private StorageDao storageDao;

    @Override
    @Scheduled(fixedDelay = 1000)
    public void removeOnTime() {
        if (storageDao.dump().size() != 0) {
            EntryDto deleteItem = storageDao.dump().first();
            if (System.currentTimeMillis() / 1000 >= deleteItem.getTtl()) {
                storageDao.remove(deleteItem.getKey());
                storageDao.dump().remove(deleteItem);
            }
        }
    }
}
