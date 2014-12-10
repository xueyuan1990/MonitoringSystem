package com.mz.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mz.entity.GroupStorage;
import com.mz.entity.Storage;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StorageServiceTest {

    @Mock
    @InjectIntoByType(target = "storageService")
    private SqlSession     sqlSession;
    @InjectMocks
    private StorageService storageService; //被测对象


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetStoragesByPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        params.put("start", 0);
        params.put("limit", 10);
        List listExp = new ArrayList();
        Storage s1 = new Storage("2014-01-01 00:00:00", 1, 1);
        Storage s2 = new Storage("2014-01-01 00:00:00", 1, 2);
        listExp.add(s1);
        listExp.add(s2);
        //mock
        Mockito.when(sqlSession.selectList("storage.getStoragesByPage", params))
                .thenReturn(listExp);

        //test
        List<Storage> list = storageService.getStoragesByPage("2014-01-01 00:00:00", 0, 10);

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("storage.getStoragesByPage", params);
    }


    @Test
    public void testGetStorage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        params.put("groupId", 1);
        params.put("serverId", 1);
        Storage sExp = new Storage("2014-01-01 00:00:00", 1, 1);
        //mock
        Mockito.when(sqlSession.selectOne("storage.getStorage", params)).thenReturn(sExp);

        //test
        Storage s = storageService.getStorage(1, 1, "2014-01-01 00:00:00");

        // verify
        ReflectionAssert.assertReflectionEquals(sExp, s);
        Mockito.verify(sqlSession).selectOne("storage.getStorage", params);

    }


    @Test
    public void testGetStoragesByGroup() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        params.put("groupId", 1);
        List listExp = new ArrayList();
        Storage s1 = new Storage("2014-01-01 00:00:00", 1, 1);
        Storage s2 = new Storage("2014-01-01 00:00:00", 1, 2);
        listExp.add(s1);
        listExp.add(s2);
        //mock
        Mockito.when(sqlSession.selectList("storage.getStoragesByGroup", params)).thenReturn(
                listExp);

        //test
        List<Storage> list = storageService.getStoragesByGroup("2014-01-01 00:00:00", 1);

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("storage.getStoragesByGroup", params);
    }


    @Test
    public void testGetStoragesPeriod() throws ParseException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", 1);
        params.put("serverId", 1);
        params.put("startTime", "2014-01-01 00:00:00");
        params.put("endTime", "2014-01-07 00:00:00");
        List listExp = new ArrayList();
        Storage s1 = new Storage("2014-01-01 00:00:00", 1, 1);
        Storage s2 = new Storage("2014-01-02 00:00:00", 1, 1);
        listExp.add(s1);
        listExp.add(s2);
        //mock
        Mockito.when(sqlSession.selectList("storage.getStoragesPeriod", params))
                .thenReturn(listExp);
        Storage s3 = new Storage("2014-01-03 00:00:00", 1, 1);
        Storage s4 = new Storage("2014-01-04 00:00:00", 1, 1);
        Storage s5 = new Storage("2014-01-05 00:00:00", 1, 1);
        Storage s6 = new Storage("2014-01-06 00:00:00", 1, 1);
        Storage s7 = new Storage("2014-01-07 00:00:00", 1, 1);
        listExp.add(s3);
        listExp.add(s4);
        listExp.add(s5);
        listExp.add(s6);
        listExp.add(s7);

        //test
        List<Storage> list = storageService.getStoragesPeriod(1, 1, "2014-01-01 00:00:00",
                "2014-01-07 00:00:00", 7);

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("storage.getStoragesPeriod", params);
    }


    @Test
    public void testGetStoragesNum() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        int numExp = 4;
        //mock
        Mockito.when(sqlSession.selectOne("storage.getStoragesNum", params)).thenReturn(numExp);

        //test
        int num = storageService.getStoragesNum("2014-01-01 00:00:00");

        // verify
        ReflectionAssert.assertReflectionEquals(numExp, num);
        Mockito.verify(sqlSession).selectOne("storage.getStoragesNum", params);
    }


    @Test
    public void testGetGroupStorages() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        List listExp = new ArrayList();
        GroupStorage gs1 = new GroupStorage("2014-01-01 00:00:00", 1, "group1", 10, 100, 50);
        GroupStorage gs2 = new GroupStorage("2014-01-01 00:00:00", 2, "group2", 10, 100, 50);
        listExp.add(gs1);
        listExp.add(gs2);

        //mock
        Mockito.when(sqlSession.selectList("storage.getGroupStorages", params)).thenReturn(listExp);

        //test
        List<GroupStorage> list = storageService.getGroupStorages("2014-01-01 00:00:00");

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("storage.getGroupStorages", params);
    }


    @Test
    public void testGetStorageOffline() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        List listExp = new ArrayList();
        Storage s = new Storage("2014-01-01 00:00:00", 2, 1, "127.0.2.1 OFFLINE");
        listExp.add(s);

        //mock
        Mockito.when(sqlSession.selectOne("storage.getLastTime")).thenReturn("2014-01-01 00:00:00");
        Mockito.when(sqlSession.selectList("storage.getStoragesOffline", params)).thenReturn(
                listExp);

        //test
        List<Storage> list = storageService.getStoragesOffline();

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectOne("storage.getLastTime");
        Mockito.verify(sqlSession).selectList("storage.getStoragesOffline", params);
    }


    @Test
    public void testGetStoragesLessFreeStorage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        List listExp = new ArrayList();
        Storage s = new Storage("2014-01-01 00:00:00", 2, 1);
        listExp.add(s);

        //mock
        Mockito.when(sqlSession.selectOne("storage.getLastTime")).thenReturn("2014-01-01 00:00:00");
        Mockito.when(sqlSession.selectList("storage.getStoragesLessFreeStorage", params))
                .thenReturn(listExp);

        //test
        List<Storage> list = storageService.getStoragesLessFreeStorage();

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectOne("storage.getLastTime");
        Mockito.verify(sqlSession).selectList("storage.getStoragesLessFreeStorage", params);
    }


    @Test
    public void testGetStoragesNginxDisconnectted() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", "2014-01-01 00:00:00");
        List listExp = new ArrayList();
        Storage s1 = new Storage("2014-01-01 00:00:00", 1, 1, "127.0.1.1 ACTIVE");
        Storage s2 = new Storage("2014-01-01 00:00:00", 1, 2, "127.0.1.2 ACTIVE");
        listExp.add(s1);
        listExp.add(s2);

        //mock
        Mockito.when(sqlSession.selectOne("storage.getLastTime")).thenReturn("2014-01-01 00:00:00");
        Mockito.when(sqlSession.selectList("storage.getStorages", params)).thenReturn(listExp);

        //test
        List<Storage> list = storageService.getStoragesNginxDisconnectted();

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectOne("storage.getLastTime");
        Mockito.verify(sqlSession).selectList("storage.getStorages", params);
    }


    @Test
    public void testGetServerThreshold() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", 1);
        params.put("serverId", 1);
        int thExp = 10;

        //mock
        Mockito.when(sqlSession.selectOne("storage.getServerThreshold", params)).thenReturn(thExp);

        //test
        int th = storageService.getServerThreshold(1, 1);

        // verify
        ReflectionAssert.assertReflectionEquals(thExp, th);
        Mockito.verify(sqlSession).selectOne("storage.getServerThreshold", params);
    }


    @Test
    public void testSetServerThreshold() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", 1);
        params.put("serverId", 1);
        params.put("serverThreshold", 10);

        //mock
        Mockito.when(sqlSession.update("storage.setServerThreshold", params)).thenReturn(1);

        //test
        boolean flag = storageService.setServerThreshold(1, 1, 10);

        // verify
        ReflectionAssert.assertReflectionEquals(true, flag);
        Mockito.verify(sqlSession).update("storage.setServerThreshold", params);
    }
}
