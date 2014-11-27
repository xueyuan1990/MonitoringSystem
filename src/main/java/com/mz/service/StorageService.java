package com.mz.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.entity.GroupStorage;
import com.mz.entity.Storage;

/**
 * Service:storage相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Service
public class StorageService {
    static Logger logger = LoggerFactory.getLogger(StorageService.class);
    @Autowired
    SqlSession    sqlSession;


    /**
     * 查询某一时刻所有服务器信息，时间格式为：2014-09-27 00:50:00
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> selectAllStorageByPage(String time, int start, int limit) {
        List<Storage> list = new ArrayList<Storage>();
        time = getFormatTime(time);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        params.put("start", start);
        params.put("limit", limit);
        list = sqlSession.selectList("storage.selectAllStorageByPage", params);

        return list;
    }


    /**
     * 查询某一时刻某台服务器信息，时间格式为：2014-09-27 00:50:00
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> selectStorageById(int groupId, int serverId, String time) {
        List<Storage> list = new ArrayList<Storage>();
        time = getFormatTime(time);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        params.put("groupId", groupId);
        params.put("serverId", serverId);
        list = sqlSession.selectList("storage.selectStorageById", params);
        return list;
    }


    /**
     * 查服务器总数
     * 
     * @author xueyuan
     * @since 1.0
     */
    public int countStorage(String time) {
        time = getFormatTime(time);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        int count = (Integer) sqlSession.selectOne("storage.countStorage", params);
        return count;
    }


    /**
     * 查询某一时刻某组服务器信息
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> selectStorageByGroup(String time, int groupId) {
        List<Storage> list = new ArrayList<Storage>();
        time = getFormatTime(time);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        params.put("groupId", groupId);
        list = sqlSession.selectList("storage.selectStorageByGroup", params);

        return list;
    }


    /**
     * 查询服务器阀值
     * 
     * @author xueyuan
     * @since 1.0
     */
    public int selectServerThreshold(int groupId, int serverId) {
        int serverThreshold = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("serverId", serverId);
        serverThreshold = (int) sqlSession.selectOne("storage.selectServerThreshold", params);
        return serverThreshold;
    }


    /**
     * 设置服务器空闲阀值，当服务器空闲容量小于该阀值时会触发报警
     * 
     * @author xueyuan
     * @since 1.0
     */
    public boolean updateServerThreshold(int groupId, int serverId, int serverThreshold) {
        boolean flag = false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("serverId", serverId);
        params.put("serverThreshold", serverThreshold);
        int i = sqlSession.update("storage.updateServerThreshold", params);
        if (i > 0) {
            flag = true;
            updateGroupThreshold(groupId);
        }
        return flag;
    }


    /**
     * 更新组阀值
     * 
     * @author xueyuan
     * @since 1.0
     */
    private void updateGroupThreshold(int groupId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        sqlSession.update("storage.updateGroupThreshold", params);
    }


    /**
     * 某段时间内某台服务器的信息，用来查看服务器容量变化趋势
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> selectStoragePeriod(int groupId, int serverId, String startTime,
                                             String endTime, int days) {
        List<Storage> list = new ArrayList<Storage>();
        endTime = getFormatTime(endTime);
        startTime = getFormatTime(startTime);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("serverId", serverId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        list = sqlSession.selectList("storage.selectStoragePeriod", params);
        String subString = "";

        if (days == 1) {
            subString = ":00:00";//小时
        } else {
            subString = "00:00:00";//天
        }
        //去掉非整小时或非整天的采样点
        int j = 0;
        while (j < list.size()) {
            if (list.get(j).getTime().indexOf(subString) == -1) {
                list.remove(j);
            } else {
                j++;
            }
        }
        // 补充为采样时间点数据
        list = checkStoragePeriod(list, groupId, serverId, startTime, endTime, days);

        if (days == 90) {//5天一次
            int i = 0;
            while (i < list.size()) {
                int date = Integer.parseInt(list.get(i).getTime().substring(8, 10));
                if ((date % 5 != 0 && date != 1) || date == 30) {
                    list.remove(i);
                } else {
                    i++;
                }
            }
        }
        return list;
    }


    /**
     * 判断序列中时间是否连续，如果不是，则补全采样点，使序列成为时间连续的
     * 
     * @author xueyuan
     * @since 1.0
     */
    private List<Storage> checkStoragePeriod(List<Storage> list, int groupId, int serverId,
                                             String startTime, String endTime, int days) {
        if (list == null) {
            list = new ArrayList<Storage>();
        }
        int timeInterval = 1;
        if (days == 1) {
            timeInterval = 3600 * 1000;
        } else {
            timeInterval = 24 * 3600 * 1000;
        }

        startTime = getFormatTime(startTime);
        endTime = getFormatTime(endTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTimeLong = 0;
        long endTimeLong = 0;
        try {
            startTimeLong = dateFormat.parse(startTime).getTime();
            endTimeLong = dateFormat.parse(endTime).getTime();//1分钟=1000*60毫秒
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        int size = (int) ((endTimeLong - startTimeLong) / timeInterval + 1);
        if (size == list.size() || startTimeLong == 0 || endTimeLong == 0) {
            return list;
        }
        int i = 0;
        String timeInList = "";
        int free = 0;
        int used = 0;
        int total = 0;
        int upload = 0;
        int download = 0;
        while (startTimeLong <= endTimeLong) {
            if (i <= list.size() - 1) {
                timeInList = list.get(i).getTime();
            }
            String timeOutList = dateFormat.format(new Date(startTimeLong)).toString();
            if (!timeInList.equals(timeOutList)) {
                Storage s = new Storage();
                s.setTime(timeOutList);// 如果不存在这个时间值，则加入
                s.setGroupId(groupId);
                s.setServerId(serverId);
                s.setFreeStorage(free);
                s.setUsedStorage(used);
                s.setTotalStorage(total);
                s.setSuccessUploadCount(upload);
                s.setSuccessDownloadCount(download);
                list.add(i, s);
            }
            i++;
            if (days == 1) {
                startTimeLong = startTimeLong + 3600 * 1000;//按小时
            } else {
                startTimeLong = startTimeLong + 24 * 3600 * 1000;//按天
            }

        }

        return list;
    }


    /**
     * 组信息
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<GroupStorage> selectGroupInfo(String time) {
        List<GroupStorage> list = new ArrayList<GroupStorage>();
        time = getFormatTime(time);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        list = sqlSession.selectList("storage.selectGroupInfo", params);

        return list;
    }


    /**
     * 把时间格式化为： yyyy-MM-dd HH:mm:ss 形式。如果时间为null，则从数据库中取出最新时间作为time值
     * 
     * @author xueyuan
     * @since 1.0
     */
    private String getFormatTime(String time) {
        if (time == null || time.trim().length() == 0) {
            //            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //            time = dateFormat.format(new Date()).toString();
            //last time
            time = (String) sqlSession.selectOne("storage.lastTime");
        }

        if (time.length() != 16) {// 将2014-1-1 1:1改为2014-01-01 01:01
            String temp = time.substring(5);// 1-1 1:1
            String[] array = temp.split("[^0-9]");
            for (int i = 0; i < array.length; i++) {
                if (array[i].length() == 1) {
                    array[i] = "0" + array[i];
                }
            }
            time = time.substring(0, 5) + array[0] + "-" + array[1] + " " + array[2] + ":"
                    + array[3];
        }
        if (time.length() == 16) {
            time = time + ":00";
        }
        return time;

    }


    /**
     * storage OFFLINE时报警
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> alertOffline() {
        String time = getFormatTime("");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        List<Storage> list = new ArrayList<Storage>();
        list = sqlSession.selectList("storage.alertOffline", params);
        return list;
    }


    /**
     * storage空闲容量低于阀值，报警
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> alertFreeStorage() {
        String time = getFormatTime("");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        List<Storage> list = new ArrayList<Storage>();
        list = sqlSession.selectList("storage.alertFreeStorage", params);
        return list;
    }


    /**
     * Nginx URL无法访问时，报警
     * 
     * @author xueyuan
     * @since 1.0
     */
    public List<Storage> alertNginx() {
        List<Storage> list = new ArrayList<Storage>();
        String time = getFormatTime("");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("time", time);
        list = sqlSession.selectList("storage.selectAllStorage", params);
        if (list == null) {
            return list;
        }
        Iterator<Storage> it = list.iterator();
        while (it.hasNext()) {
            Storage storage = it.next();
            String ip = storage.getIpAddr().split(" ")[0];
            if (getHttpCode(ip)) {//如果访问成功，则去除
                it.remove();
            }
        }
        return list;

    }


    /**
     * 该URL是否可访问，访问成功返回true，否则false
     * 
     * @author xueyuan
     * @since 1.0
     */
    private boolean getHttpCode(String surl) {
        if (surl == null) {
            return false;
        }
        if (!surl.startsWith("http://")) {
            surl = "http://" + surl;
        }
        int code = 0;
        String msg = "";
        try {
            URL url = new URL(surl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(3000);
            httpUrlConnection.setReadTimeout(3000);
            httpUrlConnection.connect();
            code = new Integer(httpUrlConnection.getResponseCode());//200
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (code == 200) {
            return true;
        } else {
            return false;
        }

    }
}
