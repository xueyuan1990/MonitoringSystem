package com.mz.entity;

/**
 * 定义GroupStorage类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class GroupStorage {
    private String time;          //获取信息的时间
    private int    groupId;       //组id
    private String groupName;     //组名
    private int    groupThreshold; //组空闲容量阀值，其值为组内各个storage服务器阀值之和
    private int    groupTotal;    //组总存储容量，为组内各个storage服务器总容量之和
    private int    groupFree;     //组空闲容量，为组内各个storage服务器空闲容量之和


    public GroupStorage() {

    }


    public GroupStorage(String time, int groupId, String groupName, int groupThreshold,
                        int groupTotal, int groupFree) {
        super();
        this.time = time;
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupThreshold = groupThreshold;
        this.groupTotal = groupTotal;
        this.groupFree = groupFree;
    }


    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }


    public int getGroupId() {
        return groupId;
    }


    public String getGroupName() {
        return groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
