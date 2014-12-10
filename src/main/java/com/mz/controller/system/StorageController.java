package com.mz.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mz.entity.GroupStorage;
import com.mz.entity.Storage;
import com.mz.service.StorageService;

/**
 * Controller:storage相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/system/storage")
public class StorageController {
    static Logger          logger = LoggerFactory.getLogger(StorageController.class);
    @Autowired
    private StorageService storageService;


    /**
     * 查询所有服务器
     * 
     * @author xueyuan
     * @since 1.0
     */
    @RequestMapping("/getStoragesByPage")
    public void getStoragesByPage(String time, int groupId, int serverId,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Storage> list = new ArrayList<Storage>();
            int count = 0;
            int start = Integer.parseInt(request.getParameter("start"));
            int limit = Integer.parseInt(request.getParameter("limit"));
            if (groupId == -1 && serverId == -1) {
                list = storageService.getStoragesByPage(time, start, limit);
                count = storageService.getStoragesNum(time);
            } else {
                Storage s = storageService.getStorage(groupId, serverId, time);
                list.add(s);
                count = 1;
            }
            map.put("rows", list);
            map.put("results", count);
            map.put("hasError", false);
            map.put("error", "");
            BaseController.writeJson(response, map);//json
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, map);
        }
    }


    /**
     * 按组别查询
     * 
     * @author xueyuan
     * @since 1.0
     */
    @RequestMapping("/getStoragesByGroup")
    public void getStoragesByGroup(String time, int groupId, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Storage> list = new ArrayList<Storage>();
            list = storageService.getStoragesByGroup(time, groupId);
            map.put("code", 200);
            map.put("message", "");
            map.put("value", list);
            BaseController.writeJson(response, map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, map);
        }
    }


    /**
     * 设置阀值
     * 
     * @author xueyuan
     * @since 1.0
     */
    @RequestMapping("/setServerThreshold")
    public void setServerThreshold(int groupId, int serverId, int serverThreshold,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> error = new HashMap<String, Object>();
        try {
            int oldServerThreshold = storageService.getServerThreshold(groupId, serverId);
            if (oldServerThreshold == serverThreshold) {
                return;
            }

            boolean flag = storageService.setServerThreshold(groupId, serverId, serverThreshold);

            String usernameOfLoginuser = (String) request.getSession().getAttribute("username");
            if (flag) {
                logger.info(usernameOfLoginuser + " update server threshold of " + groupId + "-"
                        + serverId + " to " + serverThreshold);
                error.put("code", 200);
                error.put("message", "");
                error.put("value", true);
            } else {
                error.put("code", 120000);
                error.put("message", "设置阀值失败！");
                error.put("value", false);
            }
            BaseController.writeJson(response, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, error);
        }
    }


    /**
     * 某段时间内一台服务器的信息
     * 
     * @author xueyuan
     * @since 1.0
     */
    @RequestMapping("/getStoragesPeriod")
    public void getStoragesPeriod(int groupId, int serverId, String startTime, String endTime,
                                  int days, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Storage> list = new ArrayList<Storage>();
        try {
            list = storageService.getStoragesPeriod(groupId, serverId, startTime, endTime, days);//json
            BaseController.writeJson(response, list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, list);
        }
    }


    /**
     * 查询组信息
     * 
     * @author xueyuan
     * @since 1.0
     */
    @RequestMapping("/getGroupStorages")
    public void getGroupStorages(String time, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<GroupStorage> list = new ArrayList<GroupStorage>();
            list = storageService.getGroupStorages(time);
            map.put("code", 200);
            map.put("message", "");
            map.put("value", list);
            BaseController.writeJson(response, map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, map);
        }
    }

}
