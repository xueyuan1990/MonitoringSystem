package com.mz.entity;

/**
 * 定义Tracker类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class Tracker {
    private int    trackerId;   //tracker服务器ID
    private String trackerIp;   //tracker服务器ip地址
    private String trackerState; //tracker服务器状态，如ACTIVE,OFFLINE


    public Tracker() {

    }


    public Tracker(int trackerId, String trackerIp, String trackerState) {
        super();
        this.trackerId = trackerId;
        this.trackerIp = trackerIp;
        this.trackerState = trackerState;
    }


    public int getTrackerId() {
        return trackerId;
    }


    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }


    public String getTrackerIp() {
        return trackerIp;
    }


    public void setTrackerIp(String trackerIp) {
        this.trackerIp = trackerIp;
    }


    public String getTrackerState() {
        return trackerState;
    }


    public void setTrackerState(String trackerState) {
        this.trackerState = trackerState;
    }

}
