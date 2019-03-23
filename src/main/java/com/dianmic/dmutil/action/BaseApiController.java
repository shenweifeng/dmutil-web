package com.dianmic.dmutil.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianmic.dmutil.util.CommonUtil;

/**
 * 
 * 
 * @date 2016-4-15
 * 
 * @author swf
 * 
 * @Description 基础API接口
 * 
 */
@Controller("apiController")
@RequestMapping("/api")
@Scope("prototype")
public class BaseApiController {

    Logger log = Logger.getLogger(BaseApiController.class);

    /**
     * 
     * @date 2016-4-15
     * 
     * @author swf
     * 
     * @Description 全局异常处理
     * 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, Exception e) {

        log.error(String.format("ip=[%s], sc=[%s], e=[%s].", CommonUtil.getIp(request), request.getServletContext(),
                e.getMessage()));

        e.printStackTrace();

        // 对异常进行判断做相应的处理
        if (e instanceof NullPointerException) {
            return "error404";
        } else if (e instanceof IllegalArgumentException) {
            return "error404";
        }

        return "error404";
    }

}
