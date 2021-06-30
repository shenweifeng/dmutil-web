package com.dianmic.dmutil.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dianmic.dmutil.domain.ViewExcel;
import com.dianmic.dmutil.domain.exe.CourseDept;
import com.dianmic.dmutil.util.PoiUtil;
import com.dianmic.dmutil.util.StringUtil;

/**
 * 
 * 
 * @date 2015-6-13 下午1:05:14
 * 
 * @author swf
 * 
 * @Description 工具类：无需登录身份校验
 * 
 */
@Controller("utilCourseController")
@RequestMapping("/util/course")
@Scope("prototype")
public class UtilCourseController extends BaseApiController {

    Logger                          log                         = Logger.getLogger(UtilCourseController.class);

    private String                  key_exe_map_dept_course     = "exe_map_dept_course";
    private String                  key_exe_map_dept_len_course = "exe_map_dept_len_course";
    private Map<String, CourseDept> swDeptMap                   = null;
    private int                     pathLength                  = 0;

    // 导入课程分类
    @RequestMapping(value = "/import/dept.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> course_import_dept(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<String, Object>();
        int r1 = 600;
        if (file != null && file.getSize() > 0) {
            try {
                List<List<String>> list = new ArrayList<List<String>>();
                // 总条数
                String title = file.getOriginalFilename();
                if (title.endsWith(".xlsx")) {
                    // 执行 xlsx
                    title = title.substring(0, title.indexOf(".xlsx"));
                    list = PoiUtil.read_xlsx(file.getInputStream(), 1, false);
                }

                if (list != null && !list.isEmpty()) {
                    Map<String, CourseDept> map = read_dept_course(list);
                    request.getSession().setAttribute(key_exe_map_dept_course, map);
                    request.getSession().setAttribute(key_exe_map_dept_len_course, pathLength);
                    ret.put("d1", String.format("导入课程分类总数【%s】", map.size()));
                    r1 = 200;
                    log.info(ret.get("d1"));
                }
            } catch (IOException e) {
            }
        }
        ret.put("r1", r1);
        return ret;
    }

    private Map<String, CourseDept> read_dept_course(List<List<String>> list) {
        CourseDept sd = null;
        if (swDeptMap == null) {
            swDeptMap = new HashMap<String, CourseDept>();
        }
        if (list != null && !list.isEmpty()) {
            for (List<String> s : list) {
                if (s.size() < 6) {
                    log.error(String.format("[非法数据], [%s], [%s]", s.get(0), s.get(1)));
                    continue;
                }
                sd = new CourseDept(s.get(0), s.get(1));
                if (StringUtil.isNotEmpty(s.get(3))) {
                    sd.setParentDeptNo(s.get(3));
                }
                sd.setDirNo(s.get(4));
                sd.setDirName(s.get(5));
                swDeptMap.put(sd.getDeptNo(), sd);
            }
            formatDept_sw();
        }

        return swDeptMap;
    }

    private void formatDept_sw() {
        Map<String, CourseDept> map = new HashMap<>();
        CourseDept sd = null;
        int level = 0;
        String path = null;
        CourseDept parent = null;
        for (String s : swDeptMap.keySet()) {
            sd = swDeptMap.get(s);
            if (sd.getParentDeptNo() == null) {
                // root节点
                sd.setLevel(0);
                sd.setPath(sd.getDeptNo());
                map.put(s, sd);
                continue;
            }

            if (map.containsKey(sd.getParentDeptNo())) {
                parent = map.get(sd.getParentDeptNo());
                sd.setLevel(parent.getLevel() + 1);
                sd.setPath(String.format("%s.%s", parent.getPath(), sd.getDeptNo()));
                map.put(s, sd);

                if (pathLength < sd.getLevel()) {
                    pathLength = sd.getLevel();
                }

                continue;
            }

            level = 1;
            path = sd.getDeptNo();
            parent = swDeptMap.get(sd.getParentDeptNo());
            while (parent != null) {
                ++level;
                path = String.format("%s.%s", parent.getDeptNo(), path);
                if (parent.getParentDeptNo() == null) {
                    break;
                }
                parent = swDeptMap.get(parent.getParentDeptNo());
            }
            sd.setLevel(level);
            sd.setPath(path);
            map.put(s, sd);

            if (pathLength < sd.getLevel()) {
                pathLength = sd.getLevel();
            }
        }

        swDeptMap.clear();
        swDeptMap.putAll(map);
    }

    /**
     * 
     * @date 2019年4月3日 下午12:24:42
     * 
     * @author swf
     * 
     * @Description
     * 
     * @param file
     * @param deptNoColumnName
     *            部门所在列名称
     * @param model
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/excel/xlsx.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView sw_excel_xlsx(@RequestParam("file") MultipartFile file, String deptNoColumnName, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {

                List<List<String>> list = new ArrayList<List<String>>();
                // 总条数
                String title = file.getOriginalFilename();
                if (title.endsWith(".xlsx")) {
                    // 执行 xlsx
                    title = title.substring(0, title.indexOf(".xlsx"));
                    list = PoiUtil.read_xlsx(file.getInputStream(), 0, false);
                }
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    log.info(String.format("[sw_excel_xlsx导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    swDeptMap = (Map<String, CourseDept>) request.getSession().getAttribute(key_exe_map_dept_course);
                    pathLength = (Integer) request.getSession().getAttribute(key_exe_map_dept_len_course);
                    HSSFWorkbook workbook = dealExcelSw(list, title, deptNoColumnName);
                    // log.info(title);
                    ViewExcel viewExcel = new ViewExcel();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("fileName", title);
                    try {
                        viewExcel.buildExcelDocument(params, workbook, request, response);
                    } catch (Exception e) {
                        log.error("[sw_excel_xlsx]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return new ModelAndView(viewExcel, model);

                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    private HSSFWorkbook dealExcelSw(List<List<String>> list, String title, String deptNoColumnName) {

        List<Integer> columnWidth = new ArrayList<Integer>();
        List<String> columnTitle = new ArrayList<String>();

        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        // 部门编号所在列索引值，2019年4月3日12:26:28
        int deptNoColumnIndex = -1;
        Set<String> deptNoColumnNameSet = new HashSet<String>();
        if (StringUtil.isNotEmpty(deptNoColumnName)) {
            deptNoColumnName = deptNoColumnName.trim().replaceAll("，", ",").replaceAll("、", ",");
            String[] s = deptNoColumnName.split(",");
            for (String s1 : s) {
                deptNoColumnNameSet.add(s1);
            }
        }
        for (int i = 0; i < col_len; i++) {
            columnTitle.add(colNames.get(i));
            columnWidth.add(15);
            if (deptNoColumnIndex == -1 && StringUtil.isNotEmpty(colNames.get(i)) && deptNoColumnNameSet.contains(colNames.get(i).trim())) {
                deptNoColumnIndex = i;
            }
        }
        log.info(String.format("课程分类所在列索引值=[%s]", deptNoColumnIndex));

        for (int j = 1; j <= pathLength; j++) {
            columnTitle.add(String.format("课程分类%s", j));
            columnWidth.add(15);
        }
        col_len = columnTitle.size();

        // 新数据集合
        List<String[]> datasWrite = new ArrayList<String[]>();
        String[] newData = null;

        List<List<String>> datas = new ArrayList<List<String>>();
        int size = list.size();
        List<String> temp = null;
        String path = null;
        String[] paths = null;
        String keys = null;
        for (int i = 1; i < size; i++) {
            // 序号 部门 部门名称
            // 职位 岗位 人员 姓名 有效状态 最近登录时间 累计启动次数 日均启动次数 累计在线时长 日均在线时长 页面活跃量
            // 单次页面访问量
            temp = list.get(i);
            keys = temp.get(deptNoColumnIndex);
            if (keys.contains(",")) {
                keys = keys.split(",")[0];
            }
            if (deptNoColumnIndex < 0 || !swDeptMap.containsKey(keys)) {
                // 没有部门数据
                datas.add(temp);
                log.error("error:" + temp);
                continue;
            }
            // 有部门数据

            if (swDeptMap.get(keys).getLevel() == 0) {
                // 顶级目录，显示文件夹
                temp.add(swDeptMap.get(keys).getDirName());
                temp.add(swDeptMap.get(keys).getDeptName());
            } else {
                path = swDeptMap.get(keys).getPath();
                paths = path.split("\\.");
                for (String p : paths) {
                    temp.add(swDeptMap.get(p).getDeptName());
                }
            }
            datas.add(temp);
        }
        for (List<String> s : datas) {
            newData = new String[col_len];
            for (int i = 0; i < col_len; i++) {
                if (i < s.size()) {
                    newData[i] = s.get(i);
                } else {
                    newData[i] = "";
                }
            }
            datasWrite.add(newData);
        }

        return PoiUtil.export(title, datasWrite, columnTitle);
    }

}
