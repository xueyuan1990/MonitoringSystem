package com.mz.service;

import java.util.ArrayList;
import java.util.List;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.mz.entity.Tracker;

/**
 * 测试TrackerServiceTest
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Test
@SpringApplicationContext("spring-test-datasources.xml")
public class TrackerServiceTest extends JTester {
    @SpringBeanByName("trackerService")
    TrackerService trackerService;


    @Test
    @DbFit(when = "TrackerService.selectAllTracker.when.wiki")
    public void testSelectAllTracker() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = trackerService.selectAllTracker();
        want.list(list).sizeEq(3);
    }


    @Test
    @DbFit(when = "TrackerService.alertOffline.when.wiki")
    public void testAlertOffline() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = trackerService.alertOffline();

        want.list(list).allItemsMatchAll(
                the.object().propertyMatch("trackerState", the.string().contains("OFFLINE")));

    }
}
