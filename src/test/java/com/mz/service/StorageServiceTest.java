package com.mz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.mz.entity.GroupStorage;
import com.mz.entity.Storage;

/**
 * 测试StorageService
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Test
@SpringApplicationContext({ "spring-test-datasources.xml" })
public class StorageServiceTest extends JTester {
    @SpringBeanByName("storageService")
    StorageService storageService;


    @Test
    @DbFit(when = "StorageService.selectStorageById.when.wiki")
    public void testSelectStorageById() {
        List<Storage> list = new ArrayList<Storage>();
        int groupId = 1;
        int serverId = 1;
        String time = "2014-01-01 08:00:00";
        list = storageService.selectStorageById(groupId, serverId, time);
        want.list(list).sizeEq(1);
        want.object(list.get(0)).propertyEq("time", time);//判断对象的属性
        want.object(list.get(0)).propertyEq("groupId", groupId);
        want.object(list.get(0)).propertyEq("serverId", serverId);

    }


    @Test
    @DbFit(when = "StorageService.selectStorageByGroup.when.wiki")
    public void testSelectStorageByGroup() {
        List<Storage> list = new ArrayList<Storage>();
        int groupId = 1;
        String time = "2014-01-01 08:00:00";
        list = storageService.selectStorageByGroup(time, groupId);
        want.list(list).allItemsMatchAll(the.object().propertyEq("time", time),
                the.object().propertyEq("groupId", groupId));
        want.list(list).sizeEq(2);
    }


    @Test
    @DbFit(when = "StorageService.updateServerThreshold.when.wiki", then = "StorageService.updateServerThreshold.then.wiki")
    public void testUpdateServerThreshold() {
        int groupId = 1;
        int serverId = 1;
        int serverThreshold = 90;
        storageService.updateServerThreshold(groupId, serverId, serverThreshold);

    }


    @Test
    @DbFit(when = "StorageService.countStorage.when.wiki")
    public void testCountStorage() {
        String time = "2014-01-01 08:00:00";
        int cnt = storageService.countStorage(time);
        want.number(cnt).eq(2);

    }


    @Test
    @DbFit(when = "StorageService.selectStoragePeriod1.when.wiki")
    public void testSelectStoragePeriod1() throws Exception {
        List<Storage> list = new ArrayList<Storage>();
        int groupId = 1;
        int serverId = 1;
        String startTime = "2014-01-02 06:00:00";
        String endTime = "2014-01-02 11:00:00";
        int days = 1;
        int timeInterval = 3600 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTimeLong = dateFormat.parse(startTime).getTime();

        list = storageService.selectStoragePeriod(groupId, serverId, startTime, endTime, days);

        want.list(list).allItemsMatchAll(
                the.object().propertyMatch("time", the.string().end(":00:00")));

        for (int i = 0; i < list.size(); i++) {
            Storage storage = list.get(i);
            String time = dateFormat.format(new Date(startTimeLong + timeInterval * i)).toString();
            want.object(storage).propertyEq("time", time);
            want.object(storage).propertyEq("groupId", groupId);
            want.object(storage).propertyEq("serverId", serverId);
        }
    }


    @Test
    @DbFit(when = "StorageService.selectStoragePeriod7.when.wiki")
    public void testSelectStoragePeriod7() throws Exception {
        List<Storage> list = new ArrayList<Storage>();
        int groupId = 1;
        int serverId = 1;
        String startTime = "2014-01-02 00:00:00";
        String endTime = "2014-01-09 00:00:00";
        int days = 7;
        int timeInterval = 24 * 3600 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTimeLong = dateFormat.parse(startTime).getTime();

        list = storageService.selectStoragePeriod(groupId, serverId, startTime, endTime, days);

        want.list(list).allItemsMatchAll(
                the.object().propertyMatch("time", the.string().end("00:00:00")));

        for (int i = 0; i < list.size(); i++) {
            Storage storage = list.get(i);
            String time = dateFormat.format(new Date(startTimeLong + timeInterval * i)).toString();
            want.object(storage).propertyEq("time", time);
            want.object(storage).propertyEq("groupId", groupId);
            want.object(storage).propertyEq("serverId", serverId);
        }
    }


    @Test
    @DbFit(when = "StorageService.selectGroupInfo.when.wiki")
    public void testSelectGroupInfo() throws Exception {
        List<GroupStorage> list = new ArrayList<GroupStorage>();
        String time = "2014-01-01 09:00:00";
        list = storageService.selectGroupInfo(time);
        want.list(list).sizeEq(1);
        GroupStorage g1 = list.get(0);
        want.object(g1).propertyEq("time", time);
        want.object(g1).propertyEq("groupId", 1);
        want.object(g1).propertyEq("groupThreshold", 600);
        want.object(g1).propertyEq("groupTotal", 2000);
        want.object(g1).propertyEq("groupFree", 1600);

    }


    @Test
    @DbFit(when = "StorageService.alertOffline.when.wiki")
    public void testAlertOffline() throws Exception {
        List<Storage> list = new ArrayList<Storage>();
        list = storageService.alertOffline();
        want.list(list).allItemsMatchAll(
                the.object().propertyMatch("ipAddr", the.string().contains("OFFLINE")));

    }


    @Test
    @DbFit(when = "StorageService.alertFreeStorage.when.wiki")
    public void testAlertFreeStorage() throws Exception {
        List<Storage> list = new ArrayList<Storage>();
        list = storageService.alertFreeStorage();
        Iterator<Storage> it = list.iterator();
        while (it.hasNext()) {
            Storage storage = it.next();
            int fs = storage.getFreeStorage();
            int th = storage.getServerThreshold();
            want.number(fs).isLessThan(th);
        }
    }

    //    @Test
    //    @DbFit(when = "StorageService.alertNginx.when.wiki")
    //    public void testAlertNginx() {
    //        List<Storage> list = new ArrayList<Storage>();
    //        list = storageService.alertNginx();
    //        want.list(list).sizeEq(1);
    //        want.object(list.get(0)).propertyMatch("ipAddr", the.string().contains("172.16.3.17"));
    //
    //    }

    //    @Test
    //    public void testA() {
    //        String surl = "http://172.16.3.18";
    //        boolean flag = false;
    //        flag = storageService.getHttpCode(surl);
    //        want.bool(flag).is(false);
    //    }
}
