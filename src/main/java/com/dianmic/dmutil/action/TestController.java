package com.dianmic.dmutil.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dianmic.dmutil.cache.GlobalCache;
import com.dianmic.dmutil.domain.AlarmGx;
import com.dianmic.dmutil.domain.AlarmPic;
import com.dianmic.dmutil.domain.AlarmSource;
import com.dianmic.dmutil.domain.AlarmTypeJk;
import com.dianmic.dmutil.domain.GroupAlarm;
import com.dianmic.dmutil.domain.RequestParams;
import com.dianmic.dmutil.domain.VideoJk;
import com.dianmic.dmutil.service.AlarmService;
import com.dianmic.dmutil.service.OssService;
import com.dianmic.dmutil.util.CommonUtil;
import com.dianmic.dmutil.util.Constant;
import com.dianmic.dmutil.util.DateUtil;
import com.dianmic.dmutil.util.StringUtil;

/**
 * 
 * 
 * @date 2019年1月4日
 * 
 * @author swf
 *
 * @Description 警情信息接收SDK：测试接口
 * 
 *              2019年3月22日18:44:51
 *
 */
@Controller
@RequestMapping("/test")
@Scope("prototype")
public class TestController {

    Logger               log = Logger.getLogger(TestController.class);

    private String       str;

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private OssService   ossService;

    /**
     * 
     * @date 2014-8-28 上午12:07:45
     * 
     * @author swf
     * 
     * @Description URL【POST请求】：http://125.46.78.214:85/gx/sdk/api/alarm/jk
     * 
     * @param p1
     *            设备ID【必填】
     * @param p2
     *            防区编号【非必填】
     * @param p3
     *            警情代码【必填】
     * @param p4
     *            警情时间【必填】，格式为2014-08-08 11:11:11
     * @param p5
     *            报警图片信息【非必填】，支持多文件上传
     * @param p6
     *            【视频】live_video_url【非必填】【sv.11019.net/live_go.jsp?u=
     *            alarm117@cak &p= FADA4E86003842A64EFB8859917F0F7D
     *            &device=08203edddddd&mode=video】
     * @param p7
     *            【录像】live_record_url【非必填】【sv.11019.net/live_go.jsp?u=
     *            alarm117@cak &p
     *            =FADA4E86003842A64EFB8859917F0F7D&device=08203edddddd&mode=dvr
     *            】
     * @param p8
     *            【设置】live_config_url【非必填】【sv.11019.net/live_go.jsp?u=
     *            alarm117@cak &p =FADA4E86003842A64EFB8859917F0F7D&device=
     *            08203edddddd&mode=config】
     * 
     * @return r1【200-成功，500-失败-服务器错误，600-失败-输入参数不合法】
     * 
     */
    @RequestMapping(value = "/api/alarm/jk", method = { RequestMethod.POST })
    public @ResponseBody Map<String, Object> apialarmjk(

            // String p1, String p2, String p3, String p4, @RequestParam("p5")
            // MultipartFile[] p5,
            // String p6, String p7, String p8,

            RequestParams p, HttpServletRequest request, HttpServletResponse response) {

        String p1 = p.getP1();
        String p2 = p.getP2();
        String p3 = p.getP3();
        String p4 = p.getP4();
        MultipartFile[] p5 = p.getP5();

        String p6 = p.getP6();
        String p7 = p.getP7();
        String p8 = p.getP8();

        Map<String, Object> ret = new HashMap<String, Object>();
        // IP白名单限制
        String ip = CommonUtil.getIp(request);

        str = String.format(
                "[apialarmjk], error=[ip不合法!], ip=[%s], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p6=[%s], p7=[%s], p8=[%s]",
                ip, p1, p2, p3, p4, p6, p7, p8);
        log.info(str);

        if (!GlobalCache.cacheVideoJk.containsKey(p1)) {
            str = String.format("[apialarmjk], error=[设备ID不合法!], ip=[%s], p1=[%s]", ip, p1);
            log.info(str);
            ret.put("r1", 600);
            return ret;
        }

        // BEGIN DATA RECEIVE

        String result = "600";
        VideoJk jk = GlobalCache.cacheVideoJk.get(p1);

        // 保存流水记录，2014年9月3日23:29:11
        AlarmSource objsource = new AlarmSource();
        objsource.setSource("JK");
        objsource.setIp(ip);
        objsource.setSbid(p1);

        objsource.setBjxx(p3);
        Date bjsj = DateUtil.strToDate(p4);
        if (bjsj == null) {
            bjsj = new Date();
            p4 = DateUtil.dateToString(bjsj);
        }
        objsource.setBjsj(bjsj);
        AlarmTypeJk alarmTypeJk = GlobalCache.cacheAlarmTypeJk.get(p3);
        if (alarmTypeJk != null) {
            // 设定为自定义的警情内容
            objsource.setJqnr(alarmTypeJk.getGxContent());
            objsource.setFqbh(alarmTypeJk.getFqbh());
        } else {
            // 未知报警信息
            objsource.setJqnr("未定义[" + p3 + "]");
        }
        if (jk != null && jk.getCuId() != null && GlobalCache.cacheCustomerCuIdYhbh.containsKey(jk.getCuId())) {
            objsource.setCuId(jk.getCuId());
            objsource.setYhbh(GlobalCache.cacheCustomerCuIdYhbh.get(jk.getCuId()).getYhbh());
        }
        alarmService.saveAlarmSource(objsource);
        // 保存流水记录，end，2014年9月3日23:29:11

        if (StringUtil.isNotEmpty(p1, p3, p4) && jk != null
                && GlobalCache.cacheCustomerCuIdYhbh.containsKey(jk.getCuId())) {
            List<String> pic = new ArrayList<String>();
            if (p5 != null && p5.length > 0) {
                for (MultipartFile file : p5) {
                    // log.info("save file: " + file.getOriginalFilename());
                    String fileName = ossService.saveFile(file, Constant.dir_user_img);
                    if (StringUtil.isNotEmpty(fileName) && CommonUtil.isPhoto(fileName)) {
                        pic.add(fileName);
                    }
                }
            }

            // 用户ID
            Integer cuId = jk.getCuId();
            AlarmGx obj = new AlarmGx();
            obj.setSource("JK");
            // 用户编号
            obj.setYhbh(objsource.getYhbh());
            // 警情代码
            obj.setBjxx(p3);
            // 防区编号
            obj.setFqbh(objsource.getFqbh());
            obj.setJqnr(objsource.getJqnr());
            obj.setBjsj(objsource.getBjsj());
            obj.setCuId(cuId);
            if (alarmService.saveAlarmGx(obj)) {
                // 保存成功
                result = "200";

                // 更新用户状态，2015年11月7日 15:00:02
                saveOrUpdateCustomerStatus(obj);

                if (!pic.isEmpty()) {
                    List<AlarmPic> ap = new ArrayList<AlarmPic>();
                    for (String photo : pic) {
                        ap.add(new AlarmPic(obj.getId(), photo, cuId));
                    }
                    alarmService.saveAlarmPic(ap);
                }

                // str = String
                // .format("[apialarmjk], success, p1=[%s], p2=[%s], p3=[%s],
                // p4=[%s], p5.size=[%s], p6=[%s], p7=[%s], p8=[%s]",
                // p1, p2, p3, p4, pic.size(), p6, p7, p8);
                // log.info(str);

                VideoJk v = new VideoJk();
                v.setSbid(p1);
                boolean update = false;
                if (StringUtil.isNotEmpty(p6) && !p6.equals(jk.getSpss())) {
                    // 视频实时
                    update = true;
                    v.setSpss(p6);
                    jk.setSpss(p6);
                }
                if (StringUtil.isNotEmpty(p7) && !p7.equals(jk.getSplx())) {
                    // 视频录像
                    update = true;
                    v.setSplx(p7);
                    jk.setSplx(p7);
                }
                if (StringUtil.isNotEmpty(p8) && !p8.equals(jk.getSpsz())) {
                    // 视频设置
                    update = true;
                    v.setSpsz(p8);
                    jk.setSpsz(p8);
                }

                if (CommonUtil.isVideoJkOffline(p3)) { // 离线
                    // if (jk.getIsonline() == 1) {
                    update = true;
                    v.setIsonline(2);
                    jk.setIsonline(2);
                    str = String.format("[apialarmjk], 设备离线, code=[%s]!", p3);
                    log.info(str);
                    // }
                } else { // 在线
                    if (CommonUtil.isVideoJkOnline(p3) || jk.getIsonline() == 2) {
                        update = true;
                        v.setIsonline(1);
                        jk.setIsonline(1);
                        str = String.format("[apialarmjk], 设备恢复在线, code=[%s]!", p3);
                        log.info(str);
                    }
                }

                if (update) {
                    alarmService.updateVideoJk(v);
                    GlobalCache.cacheVideoJk.put(p1, jk);
                    str = String.format("[apialarmjk], p1=[%s], [updateVideoJk], 更新URL链接成功!", p1);
                    log.info(str);
                }

                if (alarmTypeJk != null && alarmTypeJk.getAuto() == 1 && obj.getId() != null) {

                    // TODO 判断是否需要执行人工处理，如果符合人工处理逻辑，则放弃自动处理，2015年2月5日 18:58:40
                    // TODO do something
                    boolean isSelfDo = false;
                    GroupAlarm groupAlarmJk = null;
                    String nowHour = CommonUtil.getCurrentHour();

                    // CustomerGroup customerGroup =
                    // GlobalCache.cacheCustomerGroup.get(obj.getCuId());
                    // if (!customerGroup.getGroupIdSet().isEmpty()) {
                    // for (Integer gid : customerGroup.getGroupIdSet()) {
                    // if (GlobalCache.cacheGroupAlarmJk.containsKey(gid)) {
                    // groupAlarmJk = GlobalCache.cacheGroupAlarmJk.get(gid);
                    // if (groupAlarmJk.getAlarmJk() != null &&
                    // groupAlarmJk.getAlarmJk().contains(p3)
                    // && groupAlarmJk.getAlarmTime() != null
                    // && groupAlarmJk.getAlarmTime().contains(nowHour)) {
                    // // 需要手动处理
                    // isSelfDo = true;
                    // break;
                    // }
                    // }
                    // }
                    // }

                    if (isSelfDo) {
                        str = String.format("[apialarmjk], [手动处理], p1=[%s], p2=[%s], p3=[%s], p4=[%s]", p1, p2, p3, p4);
                        log.info(str);

                        // TODO 推送微信消息，2015年3月29日 23:15:55
                        // sendWeixinMessageTemplate(obj.getJqnr(),
                        // obj.getCuId(), p4, objsource.getFqbh());

                        // TODO 推送短信消息，2015年4月20日 00:47:15
                        // sendSms(obj.getJqnr(), obj.getCuId(), p4);
                        return ret;
                    }

                    // 需要自动处理的警情，执行自动处理操作
                    // alarmService.saveAlarmGxHistory(obj.getId());

                    // str = String.format(
                    // "[apialarmjk], auto doing alarm saveHistory, id=[%d],
                    // p1=[%s], p2=[%s], p3=[%s], p4=[%s]",
                    // obj.getId(), p1, p2, p3, p4);
                    // log.info(str);

                    // alarmService.removeAlarmGx(obj.getId());

                    // str = String.format(
                    // "[apialarmjk], auto doing alarm removeAlarm, id=[%d],
                    // p1=[%s], p2=[%s], p3=[%s], p4=[%s]",
                    // obj.getId(), p1, p2, p3, p4);
                    // log.info(str);
                }

                // TODO 推送微信消息，2015年3月29日 23:15:55
                // sendWeixinMessageTemplate(obj.getJqnr(), obj.getCuId(), p4,
                // objsource.getFqbh());

                // TODO 推送短信消息，2015年4月20日 00:47:15
                // sendSms(obj.getJqnr(), obj.getCuId(), p4);

            } else {
                // 保存失败
                result = "500";
                str = String.format(
                        "[apialarmjk], error=[保存失败!], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p5.size=[%s], p6=[%s], p7=[%s], p8=[%s]",
                        p1, p2, p3, p4, pic.size(), p6, p7, p8);
                log.info(str);
            }
            alarmTypeJk = null;
            obj = null;
        } else {
            str = String.format(
                    "[apialarmjk], error=[输入参数不合法!], ip=[%s], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p6=[%s], p7=[%s], p8=[%s]",
                    ip, p1, p2, p3, p4, p6, p7, p8);
            log.info(str);
        }

        // END DATA RECEIVE
        if (p5 != null && p5.length > 0) {
            int i = 1;
            for (MultipartFile mf : p5) {
                log.info(String.format("p5-file-%s=[%s]", i, mf.getOriginalFilename()));
                ++i;
            }
        }

        result = "200";

        ret.put("r1", result);
        return ret;
    }

    // 更新用户状态表，2015年11月7日 14:57:08
    private boolean saveOrUpdateCustomerStatus(AlarmGx obj) {
        if (obj == null) {
            str = String.format("[saveOrUpdateCustomerStatus], error=[更新失败!], obj=[null]");
            log.error(str);
            return false;
        }
        if (obj.getId() == null) {
            str = String.format("[saveOrUpdateCustomerStatus], error=[更新失败!], id=[null]");
            log.error(str);
            return false;
        }
        boolean ret = false;
        if ("布防".equals(obj.getJqnr()) || "撤防".equals(obj.getJqnr())) {
            ret = alarmService.saveOrUpdateCustomerStatus(obj);
            str = String.format("[saveOrUpdateCustomerStatus], ret=[%s], obj=[%s]", ret, obj);
            log.info(str);
        }

        // 更新用户最后一条报警记录信息，写入缓存中，1分钟更新一次，2016年1月9日 13:53:41
        GlobalCache.customerAlarmMap.put(obj.getCuId(), obj);

        return ret;
    }
}
