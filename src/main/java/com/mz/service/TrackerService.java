package com.mz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.entity.Tracker;

/**
 * Service:tracker相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Service
public class TrackerService {
    @Autowired
    SqlSession sqlSession;


    public List<Tracker> getTrackers() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = sqlSession.selectList("tracker.getTrackers");
        return list;
    }


    /**
     * Tracker OFFLINE时报警
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Tracker> getTrackersOffline() {
        List<Tracker> list = new ArrayList<Tracker>();
        list = sqlSession.selectList("tracker.getTrackersOffline");
        return list;
    }

}
