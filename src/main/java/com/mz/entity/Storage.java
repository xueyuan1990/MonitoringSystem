package com.mz.entity;

/**
 * 定义Storage类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class Storage {
    private String time;                //信息获取时间
    private int    groupId;             //storage服务器所属组的id
    private int    serverId;            //storage服务器组内id
    private String ipAddr;              //ip地址及状态（如ACTIVE,OFFLINE）
    private int    totalStorage;        //总存储容量
    private int    freeStorage;         //剩余空闲的存储容量
    private int    usedStorage;         //已使用的存储容量 usedStorage=totalStorage-freeStorage
    private int    serverThreshold;     //空闲容量的阀值
    private int    successUploadCount;  //上传成功的文件总数
    private int    successDownloadCount; //下载成功的文件总数
    private String lastHeartBeatTime;   //最后在线时间


    public Storage() {

    }


    public Storage(String time, int groupId, int serverId) {
        super();
        this.time = time;
        this.groupId = groupId;
        this.serverId = serverId;
    }


    public Storage(String time, int groupId, int serverId, String ipAddr) {
        super();
        this.time = time;
        this.groupId = groupId;
        this.serverId = serverId;
        this.ipAddr = ipAddr;
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
