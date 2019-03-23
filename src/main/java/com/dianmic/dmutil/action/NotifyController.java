package com.dianmic.dmutil.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianmic.dmutil.cache.GlobalCache;
import com.dianmic.dmutil.util.CommonUtil;
import com.dianmic.dmutil.util.Constant;

/**
 * 
 * @date 2014-4-3 下午5:35:33
 * 
 * @author swf
 * 
 * @Description 全局通知接口，需要授权
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

    Logger              log = Logger.getLogger(NotifyController.class);

    @Autowired
    private GlobalCache globalCache;

    @RequestMapping(value = "/{type}/{key}", method = RequestMethod.POST)
    public @ResponseBody int notifyPublic(@PathVariable Integer type, @PathVariable String key,
            HttpServletRequest request, HttpServletResponse response) {
        int ret = 600;
        String ip = CommonUtil.getIp(request);
        String name = "error";
        if (type != null && CommonUtil.checkKey4Notify(key)) {
            switch (type) {
            case 1:
                name = "initCustomer";
                globalCache.initCustomer();
                ret = 200;
                break;
            case 2:
                name = "initAlarmTypeGx";
                globalCache.initAlarmTypeGx();
                ret = 200;
                break;
            case 3:
                name = "initAppIp";
                globalCache.initAppIp();
                ret = 200;
                break;
            case 5:
                Constant.debug_log = !Constant.debug_log;
                ret = 200;
                name = "debug_log=[" + Constant.debug_log + "]";
                break;
            default:
            }
        }
        log.info(String.format("[notifyPublic], name=[%s], ret=[%d], ip=[%s]", name, ret, ip));
        return ret;
    }

    /**
     * 
     * @date 2014-8-31 下午5:45:28
     * 
     * @author swf
     * 
     * @Description 局域网内刷新本地缓存
     * 
     * @param type
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/local/{type}", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody int notifyLocal(@PathVariable Integer type, HttpServletRequest request,
            HttpServletResponse response) {
        int ret = 600;
        String ip = CommonUtil.getIp(request);
        if (ip != null && GlobalCache.whiteIp.contains(ip) && type != null) {
            switch (type) {
            case 1:
                globalCache.initCustomer();
                ret = 200;
                break;
            case 2:
                globalCache.initAlarmTypeGx();
                ret = 200;
                break;
            case 3:
                globalCache.initAppIp();
                ret = 200;
                break;
            default:
            }
        }
        log.info(String.format("[notifyLocal], ret=[%d], ip=[%s]", ret, ip));
        return ret;
    }
}
