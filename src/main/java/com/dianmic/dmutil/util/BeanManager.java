package com.dianmic.dmutil.util;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description BeanManager
 *
 */
public class BeanManager {
    protected static final Log           log = LogFactory.getLog(BeanManager.class);

    private static WebApplicationContext wac;

    public static void init(ServletContext context) {
        try {
            BeanManager.wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static Object getBean(String beanName) {
        Object obj = null;
        if (wac != null) {
            try {
                obj = wac.getBean(beanName);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return obj;
    }

    public static void refresh() {
        try {
            ((AbstractApplicationContext) wac).refresh();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
