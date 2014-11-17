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


    public String alert(HttpServletRequest request, HttpServletResponse response) {
        List<Tracker> trackerListOffline = new ArrayList<Tracker>();
        List<Storage> storageListOffline = new ArrayList<Storage>();
        List<Storage> storageListFreeStorage = new ArrayList<Storage>();
        trackerListOffline = trackerService.alertOffline();
        storageListOffline = storageService.alertOffline();
        storageListFreeStorage = storageService.alertFreeStorage();

        Map<String, Object> map = new HashMap<String, Object>();
        List codeList = new ArrayList();
        List messageList = new ArrayList();
        Map<String, String> valueMap = new HashMap<String, String>();

        if (trackerListOffline.size() == 0 && storageListOffline.size() == 0
                && storageListFreeStorage.size() == 0) {
            return "";
        } else {
            if (trackerListOffline.size() != 0) {
                codeList.add(1101);//1101:跟踪服务器 OFFLINE
                messageList.add("跟踪服务器-OFFLINE");
                Iterator<Tracker> it = trackerListOffline.iterator();
                while (it.hasNext()) {
                    Tracker tracker = it.next();
                    int trackerId = tracker.getTrackerId();
                    valueMap.put("Tracker " + trackerId, "Tracker OFFLINE");
                }
            }
            if (storageListOffline.size() != 0) {
                codeList.add(1201);//1201:存储服务器 OFFLINE
                messageList.add("存储服务器-OFFLINE");
                Iterator<Storage> it = storageListOffline.iterator();
                while (it.hasNext()) {
                    Storage storage = it.next();
                    int groupId = storage.getGroupId();
                    int serverId = storage.getServerId();
                    valueMap.put("Storage " + groupId + "-" + serverId, "Storage OFFLINE");
                }
            }
            if (storageListFreeStorage.size() != 0) {
                codeList.add(1202);//1202:存储服务器 空闲容量低于阀值
                messageList.add("存储服务器-空闲容量低于阀值");
                Iterator<Storage> it = storageListFreeStorage.iterator();
                while (it.hasNext()) {
                    Storage storage = it.next();
                    int groupId = storage.getGroupId();
                    int serverId = storage.getServerId();
                    int freeStorage = storage.getFreeStorage();
                    int serverThreshold = storage.getServerThreshold();
                    String key = "Storage " + groupId + "-" + serverId;
                    if (valueMap.containsKey(key)) {
                        String value = valueMap.get(key) + "," + "serverThreshold = "
                                + serverThreshold;
                        valueMap.put(key, value);
                    } else {
                        valueMap.put(key, "serverThreshold = " + serverThreshold);
                    }
                }
            }
            map.put("code", codeList);
            map.put("message", messageList);
            map.put("value", valueMap);
        }
        return BaseController.getJson(logger, response, map);

    }


    @RequestMapping("/alert")
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) {
        String alertInfo = alert(request, response);
        Date now = new Date();
        boolean flag = false;//为true时发送200
        if (lastTimeOfSendEmail != null
                && (now.getTime() - lastTimeOfSendEmail.getTime()) < 24 * 3600 * 1000) {
            flag = true;
        }
        Map map = new HashMap();
        map.put("code", 200);
        map.put("message", "");
        map.put("value", "");
        if (alertInfo.length() == 0 || (info.equals(alertInfo) && flag)) {//无报警信息，或相等且距离上次发送email在24小时内,发code=200
            BaseController.writeJson(logger, response, map);
        } else {
            info = alertInfo;
            lastTimeOfSendEmail = now;
            BaseController.writeJson(logger, response, info);
        }
    }
}
