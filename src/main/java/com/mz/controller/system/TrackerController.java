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
import com.mz.entity.Tracker;
import com.mz.service.TrackerService;

/**
 * Controller:tracker相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("system/tracker")
public class TrackerController {
    static Logger          logger = LoggerFactory.getLogger(TrackerController.class);
    @Autowired
    private TrackerService trackerService;


    @RequestMapping("/getTrackers")
    public void getTrackers(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Tracker> list = new ArrayList<Tracker>();
            list = trackerService.getTrackers();

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
