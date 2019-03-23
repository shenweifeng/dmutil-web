/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dianmic.dmutil.data;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dianmic.dmutil.domain.AlarmTypeGx;
import com.dianmic.dmutil.domain.AlarmTypeJk;
import com.dianmic.dmutil.domain.CacheVersion;
import com.dianmic.dmutil.domain.Customer;
import com.dianmic.dmutil.domain.SdkApp;
import com.dianmic.dmutil.domain.ServerHeartBeat;
import com.dianmic.dmutil.domain.VideoJk;
import com.dianmic.dmutil.domain.WeixinBind;

/**
 * 
 * @date 2014-3-8
 * 
 * @class com.dianmic.dmutil.CommonMapper
 * 
 * @author swf
 * 
 * @Description 工具类
 * 
 */
@Component("commonMapper")
public interface CommonMapper {

    List<AlarmTypeGx> listAlarmTypeGx();

    List<AlarmTypeJk> listAlarmTypeJk();

    List<Customer> listCustomer();

    List<SdkApp> listSdkApp();

    List<CacheVersion> listCacheVersion();

    // 刷新缓存版本号，2014年12月13日 17:00:05
    int updateCacheVersion(String cacheKey);

    List<WeixinBind> listWeixinBind();

    List<VideoJk> listVideoJk();

    int updateVideoJk(VideoJk obj);

    int updateServerHeartBeat(ServerHeartBeat obj);
}
