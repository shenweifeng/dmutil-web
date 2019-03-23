/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dianmic.dmutil.data;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dianmic.dmutil.domain.AlarmGx;
import com.dianmic.dmutil.domain.AlarmOriginal;
import com.dianmic.dmutil.domain.AlarmPic;
import com.dianmic.dmutil.domain.AlarmSource;
import com.dianmic.dmutil.domain.CustomerAlarm;
import com.dianmic.dmutil.domain.CustomerSubsystem;
import com.dianmic.dmutil.domain.CustomerSubsystemArea;

/**
 * 
 * @date 2014-8-29
 * 
 * @author swf
 * 
 * @Description
 * 
 */
@Component("alarmGxMapper")
public interface AlarmGxMapper {

    int saveAlarmGx(AlarmGx obj);

    int saveAlarmSource(AlarmSource obj);

    int saveAlarmPic(List<AlarmPic> list);

    // 自动处理，警情ID
    int callAlarmAutoDoing(Integer id);

    // 更新用户状态，2015年11月7日 14:46:02
    int saveOrUpdateCustomerStatus(AlarmGx obj);

    // 更新用户最后一条报警信息记录，2016年1月9日 13:49:31
    int saveOrUpdateCustomerAlarm(CustomerAlarm obj);

    int saveAlarmOriginal(AlarmOriginal obj);

    CustomerSubsystem getCustomerSubsystem(CustomerSubsystem obj);

    CustomerSubsystemArea getCustomerSubsystemArea(CustomerSubsystemArea obj);

}
