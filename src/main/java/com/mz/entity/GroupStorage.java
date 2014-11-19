package com.mz.entity;

/**
 * 定义GroupStorage类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class GroupStorage {
    private String time;
    private int    groupId;
    private int    groupThreshold;
    private int    groupTotal;
    private int    groupFree;


    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }


    public int getGroupId() {
        return groupId;
    }


    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    public int getGroupThreshold() {
        return groupThreshold;
    }


    public void setGroupThreshold(int groupThreshold) {
        this.groupThreshold = groupThreshold;
    }


    public int getGroupTotal() {
        return groupTotal;
    }


    public void setGroupTotal(int groupTotal) {
        this.groupTotal = groupTotal;
    }


    public int getGroupFree() {
        return groupFree;
    }


    public void setGroupFree(int groupFree) {
        this.groupFree = groupFree;
    }

}
