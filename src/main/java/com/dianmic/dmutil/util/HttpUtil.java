package com.dianmic.dmutil.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.dianmic.dmutil.cache.GlobalCache;

/**
 * 
 * 
 * @date 2014-9-1
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class HttpUtil {

    private static Logger log            = Logger.getLogger(HttpUtil.class);

    private static String default_encode = "UTF-8";

    /**
     * 
     * @date 2015-3-29 下午11:12:44
     * 
     * @author swf
     * 
     * @Description 表单提交接口，含中文
     * 
     * @param url
     * @param params
     * @return
     */
    public static boolean postForm(String url, Map<String, String> map) {

        String ret = "600";

        // HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Get请求(POST雷同)
        HttpPost post = new HttpPost(url);

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        post.setConfig(requestConfig);

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (String s : map.keySet()) {
            params.add(new BasicNameValuePair(s, map.get(s)));
        }

        HttpEntity postEntity = null;
        try {
            postEntity = new UrlEncodedFormEntity(params, default_encode);

            post.setEntity(postEntity);

            // 执行请求
            CloseableHttpResponse response = httpClient.execute(post);

            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                // 执行成功
                HttpEntity responseEntity = response.getEntity();
                ret = EntityUtils.toString(responseEntity, default_encode);
            } else {
                // 服务器错误
                ret = "500";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ret = "500";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            ret = "500";
        } catch (IOException e) {
            e.printStackTrace();
            ret = "500";
        }

        boolean result = "200".equals(ret);
        log.info(String.format("[postForm], ret=[%s], url=[%s], params=[%s]", ret, url, params));
        return result;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        String subsystem = "门店";
        String yhmc = "厦门点微信息";
        String alarmContent = "布防";
        String alarmType = "布撤防";
        String alarmTime = "2019-3-22 23:27:27";
        map.put("fromUserName", "oKgXhvuciX4tyoGj1c8o6CRCDgTg");
        map.put("templateId", GlobalCache.weixin_message_templateId_buchefang);
        map.put("shId", "1001");
        map.put("alarmType", alarmType);
        map.put("alarmTime", alarmTime);
        if (Constant.alarmType_buchefang.equals(alarmType)) {
            // 布撤防信息
            if (StringUtil.isNotEmpty(subsystem)) {
                map.put("first", String.format("%s, 已%s【%s】", yhmc, alarmContent, subsystem));
            } else {
                map.put("first", String.format("%s, 已%s", yhmc, alarmContent));
            }
        } else if (Constant.alarmType_jingqing.equals(alarmType)) {
            // 警情信息
            map.put("first", String.format("%s, 警情信息【%s】", yhmc, alarmContent));
        } else {
            map.put("first", String.format("%s, 您有一条消息通知", yhmc, alarmContent));
        }
        map.put("remark", "如有疑问，请联系客服！");
        map.put("keywordList", String.format("%s,%s,%s", alarmType, alarmContent, alarmTime));
        HttpUtil.postForm(Constant.api_weixin_sendMessageTemplate, map);
    }
}
