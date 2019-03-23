package com.dianmic.dmutil.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
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
import com.dianmic.dmutil.domain.AlarmOriginal;
import com.dianmic.dmutil.domain.AlarmPic;
import com.dianmic.dmutil.domain.AlarmSource;
import com.dianmic.dmutil.domain.AlarmTypeGx;
import com.dianmic.dmutil.domain.AlarmTypeJk;
import com.dianmic.dmutil.domain.CustomerSubsystem;
import com.dianmic.dmutil.domain.CustomerSubsystemArea;
import com.dianmic.dmutil.domain.RequestParams;
import com.dianmic.dmutil.domain.ServerHeartBeat;
import com.dianmic.dmutil.domain.VideoJk;
import com.dianmic.dmutil.job.ThreadSendWeixinMessageTemplate;
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
 * @Description 警情信息接收SDK
 *
 */
@Controller
@RequestMapping("/sdk")
@Scope("prototype")
public class SdkController {

    Logger               log = Logger.getLogger(SdkController.class);

    private String       str;

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private OssService   ossService;

    /**
     * 
     * @date 2014-8-30 下午4:50:17
     * 
     * @author swf
     * 
     * @Description 报警信息接收：DSC
     * 
     * @param p1
     *            用户编号【必填】
     * @param p2
     *            防区编号【非必填】
     * @param p3
     *            警情代码【必填】
     * @param p4
     *            警情时间【必填】，格式为2014-08-08 11:11:11
     * @param p5
     *            协议类型【必填】：CID | SIA
     * @param p6
     *            子系统【必填】：group Number
     * @param request
     * @param response
     * @return 返回值：200-成功，500-失败，600-输入参数不合法
     */
    @RequestMapping(value = "/api/alarm/dsc", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int api_alarm_dsc(String p1, String p2, String p3, String p4, String p5, String p6,
            HttpServletRequest request, HttpServletResponse response) {
        int ret = 200;// 默认200，这样可以防止重复提交，2015年7月5日 11:08:01
        // IP白名单限制
        String ip = CommonUtil.getIp(request);
        if (Constant.debug_log) {
            str = String.format(
                    "[api_alarm_dsc], [请求信息], ip=[%s], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p5=[%s], p6=[%s]", ip, p1,
                    p2, p3, p4, p5, p6);
            log.info(str);
        }
        if (StringUtil.isNotEmpty(p1, p3, p4)) {
            String protocol = "CID";
            if ("S".equals(p5)) {
                protocol = "SIA";
            }
            String subsystem = null;
            if (StringUtil.isNotEmpty(p6)) {
                subsystem = p6.trim();
            }
            String source = "DSC";
            p1 = CommonUtil.formatYhbh(p1);
            // 保存流水记录，2014年9月3日23:29:11
            AlarmSource objsource = new AlarmSource();
            objsource.setSource(source);
            objsource.setIp(ip);
            objsource.setYhbh(p1);
            objsource.setBjxx(p3);
            Date bjsj = DateUtil.strToDate(p4);
            if (bjsj == null) {
                bjsj = new Date();
                p4 = DateUtil.dateToString(bjsj);
            }
            objsource.setBjsj(bjsj);

            // 子系统，2019年3月15日19:31:02
            objsource.setSubsystem(subsystem);
            objsource.setSubsystemNew(subsystem);

            // Customer customer = null;
            Integer cuId = null;
            CustomerSubsystem cs = null;
            CustomerSubsystemArea csa = null;
            if (GlobalCache.cacheCustomerYhbhCuId.containsKey(p1)) {
                // 检索是否有子系统
                cuId = GlobalCache.cacheCustomerYhbhCuId.get(p1);
                // customer = GlobalCache.cacheCustomerCuIdYhbh.get(cuId);
                if (StringUtil.isNotEmpty(subsystem)) {
                    int sub = NumberUtils.toInt(subsystem, -1);
                    if (sub >= 0) {
                        // 有子系统
                        cs = alarmService.getCustomerSubsystem(new CustomerSubsystem(cuId, sub));
                        if (cs != null) {
                            objsource.setSubsystemNew(cs.getSubsystemName());
                        }
                    }
                }
            }

            // 防区编号
            if (StringUtil.isNotEmpty(p2)) {
                objsource.setFqbh(p2);
            }
            AlarmTypeGx alarmTypeGx = GlobalCache.cacheAlarmTypeGx.get(p3);
            if (alarmTypeGx != null) {
                objsource.setJqnr(alarmTypeGx.getAlarmType());
                objsource.setJqnrNew(alarmTypeGx.getAlarmType());

                if (alarmTypeGx.getHasArea() == 1 && StringUtil.isNotEmpty(p2)) {
                    int fq = NumberUtils.toInt(p2, -1);
                    if (fq > 0) {
                        objsource.setJqnrNew(String.format("%s[%s防区]", alarmTypeGx.getAlarmType(), fq));
                        if (cuId != null) {
                            csa = alarmService.getCustomerSubsystemArea(new CustomerSubsystemArea(cuId, fq));
                            if (csa != null) {
                                objsource.setJqnrNew(
                                        String.format("%s[%s]", alarmTypeGx.getAlarmType(), csa.getAreaName()));
                            }
                        }
                    }
                }

            } else {
                // 未知报警信息
                objsource.setJqnr("未定义[" + p3 + "]");
            }
            objsource.setCuId(GlobalCache.cacheCustomerYhbhCuId.get(p1));

            // save protocol and subsystem
            objsource.setProtocol(protocol);

            // 保存流水记录，end，2014年9月3日23:29:11
            alarmService.saveAlarmSource(objsource);

            if (!GlobalCache.cacheCustomerYhbhCuId.containsKey(p1)) {
                str = String.format(
                        "[api_alarm_dsc], error=[用户编号不存在!], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p5=[%s], p6=[%s]", p1,
                        p2, p3, p4, p5, p6);
                log.error(str);
                return ret;
            }

            AlarmGx obj = new AlarmGx();
            obj.setSource(source);
            obj.setYhbh(p1);
            obj.setBjxx(p3);
            obj.setBjsj(bjsj);
            obj.setFqbh(objsource.getFqbh());
            obj.setCuId(objsource.getCuId());
            obj.setJqnr(objsource.getJqnr());
            obj.setJqnrNew(objsource.getJqnrNew());

            // save protocol and subsystem
            obj.setProtocol(protocol);
            obj.setSubsystem(objsource.getSubsystem());
            obj.setSubsystemNew(objsource.getSubsystemNew());

            if (alarmService.saveAlarmGx(obj)) {
                // 保存成功
                ret = 200;

                // 更新用户状态，2015年11月7日 15:00:02
                saveOrUpdateCustomerStatus(obj);

                if (alarmTypeGx != null && alarmTypeGx.getAuto() == 1 && obj.getId() != null) {
                    // 需要自动处理的警情，执行自动处理操作
                    alarmService.callAlarmAutoDoing(obj.getId());
                }

                // 推送微信消息，2015年3月29日 23:15:55
                sendWeixinMessageTemplate(obj.getJqnr(), obj.getCuId(), p4, obj.getSubsystemNew());

            } else {
                str = String.format(
                        "[api_alarm_dsc], error=[保存失败!], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p5=[%s], p6=[%s]", p1, p2,
                        p3, p4, p5, p6);
                log.error(str);
            }
            alarmTypeGx = null;
            obj = null;
        } else {
            str = String.format(
                    "[api_alarm_dsc], error=[输入参数不合法!], ip=[%s], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p5=[%s], p6=[%s]",
                    ip, p1, p2, p3, p4, p5, p6);
            log.error(str);
        }
        return ret;
    }

    /**
     * 
     * @date 2019年1月4日 下午5:01:20
     * 
     * @author swf
     * 
     * @Description 推送微信消息
     * 
     * @param alarmContent
     * @param cuId
     * @param alarmTime
     * @param fqbh
     */
    private void sendWeixinMessageTemplate(String alarmContent, Integer cuId, String alarmTime, String subsystem) {
        boolean send = true;
        if (alarmContent.contains("测试") || alarmContent.contains("未定义")) {
            send = false;
        }
        if (!send) {
            return;
        }
        String alarmType = Constant.alarmType_jingqing;
        if (alarmContent.contains("布防") || alarmContent.contains("撤防")) {
            alarmType = Constant.alarmType_buchefang;
        }
        if (send && GlobalCache.cacheWeixinBind.containsKey(cuId)
                && GlobalCache.cacheWeixinMessageTemplateIds.containsKey(alarmContent)
                && GlobalCache.cacheCustomerCuIdYhbh.containsKey(cuId)) {
            int yhbh = NumberUtils.toInt(GlobalCache.cacheCustomerCuIdYhbh.get(cuId).getYhbh());
            new ThreadSendWeixinMessageTemplate(alarmContent,
                    String.format("[%s]%s", yhbh, GlobalCache.cacheCustomerCuIdYhbh.get(cuId).getYhmc()), alarmTime,
                    GlobalCache.cacheWeixinBind.get(cuId), subsystem, alarmType).start();
        }
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

    /**
     * 
     * @date 2019年1月22日 上午7:28:25
     * 
     * @author swf
     * 
     * @Description 保存原始警情记录
     * 
     * @param p1
     *            警情
     * @param p2
     *            时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/alarm/source", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int api_alarm_source(String p1, String p2, HttpServletRequest request,
            HttpServletResponse response) {
        int ret = 200;// 默认200，这样可以防止重复提交，2015年7月5日 11:08:01
        // IP白名单限制
        String ip = CommonUtil.getIp(request);
        if (Constant.debug_log) {
            str = String.format("[api_alarm_source], [请求信息], ip=[%s], p1=[%s], p2=[%s]", ip, p1, p2);
            log.info(str);
        }
        if (StringUtil.isNotEmpty(p1, p2)) {
            // save
            alarmService.saveAlarmOriginal(new AlarmOriginal(p1, p2, ip));
        } else {
            str = String.format("[api_alarm_source], error=[输入参数不合法!], ip=[%s], p1=[%s], p2=[%s]", ip, p1, p2);
            log.error(str);
        }
        return ret;
    }

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
    @RequestMapping(value = "/api/alarm/jk", method = { RequestMethod.POST, RequestMethod.GET })
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

        // error=[ip不合法!],
        str = String.format("[apialarmjk], ip=[%s], p1=[%s], p2=[%s], p3=[%s], p4=[%s], p6=[%s], p7=[%s], p8=[%s]", ip,
                p1, p2, p3, p4, p6, p7, p8);
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

        String subsystem = null;
        String subsystemNew = null;

        // Customer customer = null;
        Integer cuId = null;
        CustomerSubsystem cs = null;
        CustomerSubsystemArea csa = null;

        if (jk != null && jk.getCuId() != null && GlobalCache.cacheCustomerCuIdYhbh.containsKey(jk.getCuId())) {
            cuId = jk.getCuId();
            objsource.setCuId(jk.getCuId());
            objsource.setYhbh(GlobalCache.cacheCustomerCuIdYhbh.get(jk.getCuId()).getYhbh());
        }

        AlarmTypeJk alarmTypeJk = GlobalCache.cacheAlarmTypeJk.get(p3);
        if (alarmTypeJk != null) {
            // 设定为自定义的警情内容
            objsource.setJqnr(alarmTypeJk.getGxContent());
            objsource.setJqnrNew(alarmTypeJk.getGxContent());
            objsource.setFqbh(alarmTypeJk.getFqbh());

            if (alarmTypeJk.getHasArea() == 1) {
                int fq = NumberUtils.toInt(alarmTypeJk.getFqbh(), -1);
                if (fq >= 0 && cuId != null) {
                    // 有防区
                    objsource.setJqnrNew(String.format("%s[%s防区]", alarmTypeJk.getGxContent(), fq));
                    csa = alarmService.getCustomerSubsystemArea(new CustomerSubsystemArea(cuId, fq));
                    if (csa != null) {
                        subsystem = String.valueOf(csa.getSubsystemCode());
                        subsystemNew = csa.getSubsystemName();
                        objsource.setJqnrNew(String.format("%s[%s]", alarmTypeJk.getGxContent(), csa.getAreaName()));
                    }
                }
            } else {
                // 子系统
                subsystem = alarmTypeJk.getFqbh();

                int fq = NumberUtils.toInt(subsystem, -1);
                if (fq >= 0 && cuId != null) {
                    // 有防区
                    cs = alarmService.getCustomerSubsystem(new CustomerSubsystem(cuId, fq));
                    if (cs != null) {
                        subsystemNew = cs.getSubsystemName();
                    }
                }
            }
        } else {
            // 未知报警信息
            objsource.setJqnr("未定义[" + p3 + "]");
        }

        String protocol = "JK";

        objsource.setProtocol(protocol);

        objsource.setSubsystem(subsystem);
        objsource.setSubsystemNew(subsystemNew);

        alarmService.saveAlarmSource(objsource);
        // 保存流水记录，end，2014年9月3日23:29:11

        if (StringUtil.isNotEmpty(p1, p3, p4) && jk != null
                && GlobalCache.cacheCustomerCuIdYhbh.containsKey(jk.getCuId())) {
            List<String> pic = new ArrayList<String>();
            if (p5 != null && p5.length > 0) {
                String fn = null;
                String fileName = null;
                for (MultipartFile file : p5) {
                    fn = file.getOriginalFilename();
                    if (StringUtil.isNotEmpty(fn) && CommonUtil.isPhoto(fn)) {
                        fileName = objsource.getSbid() + "_" + fn;
                        fileName = ossService.saveFileWithFileName(file, Constant.dir_user_img, fileName);
                        if (StringUtil.isNotEmpty(fileName)) {
                            pic.add(fileName);
                        }
                    }
                    fn = null;
                    fileName = null;
                }
            }

            // 用户ID
            AlarmGx obj = new AlarmGx();
            obj.setSource("JK");
            // 用户编号
            obj.setYhbh(objsource.getYhbh());
            // 警情代码
            obj.setBjxx(p3);
            // 防区编号
            obj.setFqbh(objsource.getFqbh());
            obj.setJqnr(objsource.getJqnr());
            obj.setJqnrNew(objsource.getJqnrNew());
            obj.setBjsj(objsource.getBjsj());
            obj.setCuId(cuId);

            obj.setProtocol(protocol);
            obj.setSubsystem(objsource.getSubsystem());
            obj.setSubsystemNew(objsource.getSubsystemNew());

            if (alarmService.saveAlarmGx(obj)) {
                // 保存成功
                result = "200";

                // 更新用户状态，2015年11月7日 15:00:02
                saveOrUpdateCustomerStatus(obj);

                if (!pic.isEmpty()) {
                    List<String> picSave = CommonUtil.sortAlarmPic(pic);
                    List<AlarmPic> ap = new ArrayList<AlarmPic>();
                    for (String photo : picSave) {
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
                    // 需要自动处理的警情，执行自动处理操作
                    // alarmService.saveAlarmGxHistory(obj.getId());
                    alarmService.callAlarmAutoDoing(obj.getId());

                }

                // TODO 推送微信消息，2015年3月29日 23:15:55

                // 推送微信消息，2015年3月29日 23:15:55
                sendWeixinMessageTemplate(obj.getJqnr(), obj.getCuId(), p4, objsource.getSubsystemNew());

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

    /**
     * 
     * @date 2019年3月1日 下午11:30:20
     * 
     * @author swf
     * 
     * @Description 心跳接口
     * 
     * @param t
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hb", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int heartbeat(String t, HttpServletRequest request, HttpServletResponse response) {
        int ret = 200;// 默认200，这样可以防止重复提交，2015年7月5日 11:08:01
        // IP白名单限制
        String ip = CommonUtil.getIp(request);
        // log.info(String.format("[hb], ip=[%s], t=[%s], st=[%s]", ip, t,
        // StringUtil.getCurrentDayTime()));
        alarmService.updateServerHeartBeat(new ServerHeartBeat(Constant.serverId_dsc, ip, t, new Date()));
        return ret;
    }

    /**
     * 
     * @date 2019年3月11日 下午4:45:59
     * 
     * @author swf
     * 
     * @Description 获取当前的心跳时间
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hbc", method = { RequestMethod.GET })
    public @ResponseBody String heartbeatCurrent(HttpServletRequest request, HttpServletResponse response) {
        String ret = "600";
        // log.info(String.format("[hb], ip=[%s], t=[%s], st=[%s]", ip, t,
        // StringUtil.getCurrentDayTime()));
        return ret;
    }
}
