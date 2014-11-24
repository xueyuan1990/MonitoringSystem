package com.mz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mz.common.BaseController;
import com.mz.entity.Storage;
import com.mz.entity.Tracker;
import com.mz.service.StorageService;
import com.mz.service.TrackerService;

/**
 * Controller:报警功能
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/alert")
public class AlertController {
    static Logger          logger = LoggerFactory.getLogger(AlertController.class);
    static String          info   = "";
    static Date            lastTimeOfSendEmail;
    @Autowired
    private StorageService storageService;
    @Autowired
    private TrackerService trackerService;


    public Map<String, Object> getAlertInfo(HttpServletRequest request, HttpServletResponse response) {
        List<Tracker> trackerListOffline = new ArrayList<Tracker>();
        List<Storage> storageListOffline = new ArrayList<Storage>();
        List<Storage> storageListFreeStorage = new ArrayList<Storage>();
        List<Storage> storageListNginx = new ArrayList<Storage>();
        trackerListOffline = trackerService.alertOffline();
        storageListOffline = storageService.alertOffline();
        storageListFreeStorage = storageService.alertFreeStorage();
        storageListNginx = storageService.alertNginx();

        Map<String, Object> alertInfoMap = new HashMap<String, Object>();
        List codeList = new ArrayList();
        Map<String, String> valueMap = new HashMap<String, String>();
        if ((trackerListOffline == null || trackerListOffline.size() == 0)
                && (storageListOffline == null || storageListOffline.size() == 0)
                && (storageListFreeStorage == null || storageListFreeStorage.size() == 0)
                && (storageListNginx == null || storageListNginx.size() == 0)) {
            alertInfoMap.put("code", 200);
            alertInfoMap.put("message", "状态正常");
            alertInfoMap.put("value", "");
        } else {
            if (trackerListOffline != null && trackerListOffline.size() != 0) {
                codeList.add(1101);//1101:跟踪服务器 OFFLINE
                Iterator<Tracker> it = trackerListOffline.iterator();
                while (it.hasNext()) {
                    Tracker tracker = it.next();
                    int trackerId = tracker.getTrackerId();
                    valueMap.put("Tracker " + trackerId, "OFFLINE");
                }
            }
            if (storageListOffline != null && storageListOffline.size() != 0) {
                codeList.add(1201);//1201:存储服务器 OFFLINE
                Iterator<Storage> it = storageListOffline.iterator();
                while (it.hasNext()) {
                    Storage storage = it.next();
                    int groupId = storage.getGroupId();
                    int serverId = storage.getServerId();
                    valueMap.put("Storage " + groupId + "-" + serverId, "OFFLINE");
                }
            }
            if (storageListFreeStorage != null && storageListFreeStorage.size() != 0) {
                codeList.add(1202);//1202:存储服务器 空闲容量低于阀值
                Iterator<Storage> it = storageListFreeStorage.iterator();
                while (it.hasNext()) {
                    Storage storage = it.next();
                    int groupId = storage.getGroupId();
                    int serverId = storage.getServerId();
                    int freeStorage = storage.getFreeStorage();
                    int serverThreshold = storage.getServerThreshold();
                    String key = "Storage " + groupId + "-" + serverId;
                    if (valueMap.containsKey(key)) {
                        String value = valueMap.get(key) + "," + "空闲容量低于阀值 " + serverThreshold;
                        valueMap.put(key, value);
                    } else {
                        valueMap.put(key, "空闲容量低于阀值 " + serverThreshold);
                    }
                }
            }
            if (storageListNginx != null && storageListNginx.size() != 0) {
                codeList.add(1203);//1203:存储服务器 Nginx失效
                Iterator<Storage> it = storageListNginx.iterator();
                while (it.hasNext()) {
                    Storage storage = it.next();
                    int groupId = storage.getGroupId();
                    int serverId = storage.getServerId();
                    String ip = storage.getIpAddr().split(" ")[0];
                    String key = "Storage " + groupId + "-" + serverId;
                    if (valueMap.containsKey(key)) {
                        String value = valueMap.get(key) + "," + "访问Nginx(" + ip + ")失败";
                        valueMap.put(key, value);
                    } else {
                        valueMap.put(key, "访问Nginx(" + ip + ")失败");
                    }
                }

            }
            alertInfoMap.put("code", codeList);
            alertInfoMap.put("message", "状态异常");
            alertInfoMap.put("value", valueMap);
        }
        return alertInfoMap;

    }


    @RequestMapping("/alert")
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) {
        Date now = new Date();
        boolean flag = false;//为true时发送200
        if (lastTimeOfSendEmail != null
                && (now.getTime() - lastTimeOfSendEmail.getTime()) < 24 * 3600 * 1000) {
            flag = true;
        }
        Map<String, Object> alertInfoMap = getAlertInfo(request, response);
        String alertInfo = BaseController.getJson(logger, response, alertInfoMap);

        if (info != null && info.equals(alertInfo) && flag) {//相等且距离上次发送email在24小时内,发code=200
            alertInfoMap.put("code", 200);
        } else {
            info = alertInfo;
            lastTimeOfSendEmail = now;
        }
        BaseController.writeJson(logger, response, alertInfoMap);
    }
}
