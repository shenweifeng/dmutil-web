package com.dianmic.dmutil.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description Ip获取工具
 *
 */
public class IpConvert {

    public static JSONObject getLocation(String ip) {
        JSONObject result = null;
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
        try {
            String info = getHtml(url, "utf-8");
            if (StringUtils.isNotBlank(info)) {
                result = JSONObject.parseObject(info);
            }
        } catch (Exception e) {
        }
        return result;
    }

    static String getHtml(String url, String encode) throws HttpException, IOException {

        // HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Get请求(POST雷同)
        HttpGet httpGet = new HttpGet(url);

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        // 执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            // 执行成功
            HttpEntity entity = response.getEntity();
            String ret = EntityUtils.toString(entity, encode);
            return ret;
        }
        return null;
    }

}
