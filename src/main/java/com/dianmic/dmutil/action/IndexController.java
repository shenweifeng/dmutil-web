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
@Controller("indexController")
@RequestMapping("/")
@Scope("prototype")
public class IndexController extends BaseApiController {

    Logger log = Logger.getLogger(IndexController.class);

    /**
     * 
     * @date 2017年11月18日 上午9:06:16
     * 
     * @author swf
     * 
     * @Description 网站根目录地址 /
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "", "/index.html" }, method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("main");
        mav.addObject("title", Constant.project_title);
        return mav;
    }

}
