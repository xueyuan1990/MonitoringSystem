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
    @DbFit(when = "TrackerService.getTrackers.when.wiki")
    public void testGetTrackers() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = trackerService.getTrackers();
        want.list(list).sizeEq(3);
    }


    @Test
    @DbFit(when = "TrackerService.getTrackersOffline.when.wiki")
    public void testGetTrackersOffline() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = trackerService.getTrackersOffline();

        want.list(list).allItemsMatchAll(
                the.object().propertyMatch("trackerState", the.string().contains("OFFLINE")));

    }
}
