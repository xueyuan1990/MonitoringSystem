package com.mz.entity;

/**
 * 定义Storage类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class Storage {
    private String time;
    private int    groupId;
    private int    serverId;
    private String ipAddr;
    private int    totalStorage;
    private int    freeStorage;
    private int    usedStorage;         //total-free
    private int    serverThreshold;
    private int    successUploadCount;
    private int    successDownloadCount;
    private String lastHeartBeatTime;


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


    public int getServerId() {
        return serverId;
    }


    public void setServerId(int serverId) {
        this.serverId = serverId;
    }


    public String getIpAddr() {
        return ipAddr;
    }


    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }


    public int getTotalStorage() {
        return totalStorage;
    }


    public void setTotalStorage(int totalStorage) {
        this.totalStorage = totalStorage;
    }


    public int getFreeStorage() {
        return freeStorage;
    }


    public void setFreeStorage(int freeStorage) {
        this.freeStorage = freeStorage;
    }


    public int getUsedStorage() {
        return usedStorage;
    }


    public void setUsedStorage(int usedStorage) {
        this.usedStorage = usedStorage;
    }


    public int getServerThreshold() {
        return serverThreshold;
    }


    public void setServerThreshold(int serverThreshold) {
        this.serverThreshold = serverThreshold;
    }


    public int getSuccessUploadCount() {
        return successUploadCount;
    }


    public void setSuccessUploadCount(int successUploadCount) {
        this.successUploadCount = successUploadCount;
    }


    public int getSuccessDownloadCount() {
        return successDownloadCount;
    }


    public void setSuccessDownloadCount(int successDownloadCount) {
        this.successDownloadCount = successDownloadCount;
    }


    public String getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }


    public void setLastHeartBeatTime(String lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

}
