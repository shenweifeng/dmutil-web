/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dianmic.dmutil.data;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dianmic.dmutil.domain.CacheVersion;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 工具类
 *
 */
@Component("commonMapper")
public interface CommonMapper {

    List<CacheVersion> listCacheVersion();

}
