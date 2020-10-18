package com.dianmic.dmutil.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dianmic.dmutil.util.Constant;

/**
 * 
 * 
 * @date 2015-6-13 下午1:05:14
 * 
 * @author swf
 * 
 * @Description 公共逻辑：无需登录身份校验
 * 
 */
@Controller("jimmyController")
@RequestMapping("/jimmy")
@Scope("prototype")
public class JimmyController extends BaseApiController {

    Logger         log         = Logger.getLogger(JimmyController.class);
    private String page_prefix = "jimmy/";

    @RequestMapping(value = { "/index" }, method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView jimmy(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("jimmy");
        mav.addObject("title", Constant.project_title);
        return mav;
    }

    @RequestMapping(value = { "/math.html" }, method = { RequestMethod.GET })
    public ModelAndView math(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(page_prefix + "math");
        return mav;
    }
}
