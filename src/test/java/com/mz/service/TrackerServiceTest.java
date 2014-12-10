package com.mz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.reflectionassert.ReflectionAssert;

import com.mz.entity.Tracker;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TrackerServiceTest {

    @Mock
    @InjectIntoByType(target = "trackerService")
    private SqlSession     sqlSession;
    @InjectMocks
    private TrackerService trackerService; //被测对象


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetTrackers() {
        List listExp = new ArrayList();
        Tracker t1 = new Tracker(1, "127.0.1.1", "ACTIVE");
        Tracker t2 = new Tracker(2, "127.0.1.2", "ACTIVE");
        listExp.add(t1);
        listExp.add(t2);

        //mock
        Mockito.when(sqlSession.selectList("tracker.getTrackers")).thenReturn(listExp);

        //test
        List list = trackerService.getTrackers();

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("tracker.getTrackers");
    }


    @Test
    public void testGetTrackersOffline() {
        List listExp = new ArrayList();
        Tracker t1 = new Tracker(1, "127.0.1.1", "OFFLINE");
        Tracker t2 = new Tracker(2, "127.0.1.2", "OFFLINE");
        listExp.add(t1);
        listExp.add(t2);

        //mock
        Mockito.when(sqlSession.selectList("tracker.getTrackersOffline")).thenReturn(listExp);

        //test
        List list = trackerService.getTrackersOffline();

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("tracker.getTrackersOffline");
    }
}
