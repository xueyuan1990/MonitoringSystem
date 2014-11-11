package com.mz.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mz.entity.Storage;
import com.mz.test.common.SpringBaseTest;

public class StorageServiceTest extends SpringBaseTest {
    @Autowired
    StorageService storageService;


    @Test
    public void checkStoragePeriodTest() throws Exception {
        List<Storage> list = new ArrayList<Storage>();
        Storage s1 = new Storage();
        Storage s2 = new Storage();
        Storage s3 = new Storage();
        //        s1.setTime("2014-01-01 08:00:00");
        //        s2.setTime("2014-01-01 09:00:00");
        //        s3.setTime("2014-01-01 11:00:00");
        //        s1.setFreeStorage(1);
        //        s2.setFreeStorage(5);
        //        s3.setFreeStorage(10);
        list.add(s1);
        list.add(s2);
        list.add(s3);

        list = storageService.checkStoragePeriod(list, "2014-01-01 06:00:00",
                "2014-01-01 11:00:00", 1);
        //        for (int i = 0; i < list.size(); i++) {
        //            System.out.println(list.get(i).getTime() + "---" + list.get(i).getFreeStorage());
        //        }

    }
}
