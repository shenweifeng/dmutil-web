package com.dianmic.dmutil.job;

import java.util.Set;

import org.apache.log4j.Logger;

import com.dianmic.dmutil.util.CommonUtil;

/**
 * 
 * 
 * @date 2015-3-29
 * 
 * @author swf
 * 
 * @Description 微信-推送模板消息【布撤防】
 * 
 */
public class ThreadSendWeixinMessageTemplate extends Thread {

    Logger              log = Logger.getLogger(ThreadSendWeixinMessageTemplate.class);

    private String      alarmContent;
    private String      yhmc;
    private String      alarmTime;
    private Set<String> fromUserNames;
    private String      subsystem;
    private String      alarmType;

    public ThreadSendWeixinMessageTemplate(String alarmContent, String yhmc, String alarmTime,
            Set<String> fromUserNames, String subsystem, String alarmType) {
        this.alarmContent = alarmContent;
        this.fromUserNames = fromUserNames;
        this.yhmc = yhmc;
        this.alarmTime = alarmTime;
        this.subsystem = subsystem;
        this.alarmType = alarmType;
    }

    @Override
    public void run() {
        CommonUtil.sendWeixinMessageTemplate(alarmContent, yhmc, alarmTime, fromUserNames, subsystem, alarmType);
    }

}
