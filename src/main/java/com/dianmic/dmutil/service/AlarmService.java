package com.dianmic.dmutil.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianmic.dmutil.data.AlarmGxMapper;
import com.dianmic.dmutil.data.CommonMapper;
import com.dianmic.dmutil.domain.AlarmGx;
import com.dianmic.dmutil.domain.AlarmOriginal;
import com.dianmic.dmutil.domain.AlarmPic;
import com.dianmic.dmutil.domain.AlarmSource;
import com.dianmic.dmutil.domain.CustomerAlarm;
import com.dianmic.dmutil.domain.CustomerSubsystem;
import com.dianmic.dmutil.domain.CustomerSubsystemArea;
import com.dianmic.dmutil.domain.ServerHeartBeat;
import com.dianmic.dmutil.domain.VideoJk;
import com.dianmic.dmutil.util.Constant;

@Service("alarmService")
public class AlarmService {

    Logger                log = Logger.getLogger(AlarmService.class);

    // 共信
    @Autowired
    private AlarmGxMapper alarmGxMapper;
    @Autowired
    private CommonMapper  commonMapper;

    public boolean saveAlarmGx(AlarmGx obj) {
        return alarmGxMapper.saveAlarmGx(obj) > 0;
    }

    public boolean saveAlarmSource(AlarmSource obj) {
        return alarmGxMapper.saveAlarmSource(obj) > 0;
    }

    public int saveAlarmPic(List<AlarmPic> list) {
        return alarmGxMapper.saveAlarmPic(list);
    }

    public boolean callAlarmAutoDoing(Integer id) {
        if (id == null) {
            return false;
        }
        return alarmGxMapper.callAlarmAutoDoing(id) > 0;
    }

    // 更新用户状态表，2015年11月7日 14:57:08
    public boolean saveOrUpdateCustomerStatus(AlarmGx obj) {
        if (obj == null) {
            return false;
        }
        alarmGxMapper.saveOrUpdateCustomerStatus(obj);
        return true;
    }

    // 更新用户最后一条报警信息记录，2016年1月9日 13:49:31
    public boolean saveOrUpdateCustomerAlarm(CustomerAlarm obj) {
        if (obj == null) {
            return false;
        }
        alarmGxMapper.saveOrUpdateCustomerAlarm(obj);
        return true;
    }

    public boolean saveAlarmOriginal(AlarmOriginal obj) {
        return alarmGxMapper.saveAlarmOriginal(obj) > 0;
    }

    public int updateVideoJk(VideoJk obj) {

        // 更新完数据，需要更新cache_version，让其他应用去刷新缓存
        int ret = commonMapper.updateVideoJk(obj);

        // 刷新缓存版本号，2014年12月13日 17:01:49
        commonMapper.updateCacheVersion(Constant.cache_key_videoJk);

        return ret;
    }

    public int updateServerHeartBeat(ServerHeartBeat obj) {
        return commonMapper.updateServerHeartBeat(obj);
    }

    public CustomerSubsystem getCustomerSubsystem(CustomerSubsystem obj) {
        return alarmGxMapper.getCustomerSubsystem(obj);
    }

    public CustomerSubsystemArea getCustomerSubsystemArea(CustomerSubsystemArea obj) {
        return alarmGxMapper.getCustomerSubsystemArea(obj);
    }
}
