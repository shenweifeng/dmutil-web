package com.dianmic.dmutil.job;

import org.apache.log4j.Logger;

import com.dianmic.dmutil.util.CommonUtil;

/**
 * 
 * 
 * @date 2015-4-16
 * 
 * @author swf
 * 
 * @Description 短信-推送【布撤防】
 * 
 */
public class ThreadSendSms extends Thread {

    Logger           log = Logger.getLogger(ThreadSendSms.class);

    private String[] mobiles;
    private String   content;

    public ThreadSendSms(String[] mobiles, String content) {
        this.mobiles = mobiles;
        this.content = content;
    }

    @Override
    public void run() {
        for (String mobile : mobiles) {
            CommonUtil.sendSmsMessage(mobile, content);
        }
    }

}
