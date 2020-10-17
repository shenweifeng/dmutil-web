package com.dianmic.dmutil.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.dianmic.dmutil.domain.exe.ExeDept;
import com.dianmic.dmutil.domain.exe.SwDept;
import com.dianmic.dmutil.util.DateUtil;
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
@Controller("utilController")
@RequestMapping("/util")
@Scope("prototype")
public class UtilController extends BaseApiController {

    Logger                       log                     = Logger.getLogger(UtilController.class);

    private String               page_prefix             = "util/";

    private String               key_exe_map_dept        = "exe_map_dept";
    private String               key_exe_map_dept_sw     = "exe_map_dept_sw";
    private String               key_exe_map_dept_len_sw = "exe_map_dept_len_sw";
    private Map<String, ExeDept> deptMap                 = null;
    private Map<String, SwDept>  swDeptMap               = null;
    private int                  pathLength              = 0;

    @RequestMapping(value = { "/anta.html" }, method = { RequestMethod.GET })
    public ModelAndView anta(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(page_prefix + "anta");
        return mav;
    }

    @RequestMapping(value = { "/sw.html" }, method = { RequestMethod.GET })
    public ModelAndView sw(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(page_prefix + "sw");
        return mav;
    }

    @RequestMapping(value = { "/jf.html" }, method = { RequestMethod.GET })
    public ModelAndView jf(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(page_prefix + "jf");
        return mav;
    }

    @RequestMapping(value = { "/dm.html" }, method = { RequestMethod.GET })
    public ModelAndView dm(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(page_prefix + "dm");
        return mav;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/excel/xlsx.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView excel_xlsx(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {
                List<List<String>> list = PoiUtil.read_xlsx(file.getInputStream(), 0);
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    String title = file.getOriginalFilename();
                    title = title.substring(0, title.indexOf(".xlsx"));
                    log.info(String.format("[xlsx导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    deptMap = (Map<String, ExeDept>) request.getSession().getAttribute(key_exe_map_dept);
                    HSSFWorkbook workbook = dealExcel(list, title);
                    // log.info(title);
                    ViewExcel viewExcel = new ViewExcel();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("fileName", title);
                    try {
                        viewExcel.buildExcelDocument(params, workbook, request, response);
                    } catch (Exception e) {
                        log.error("[excel_xlsx]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return new ModelAndView(viewExcel, model);

                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    private HSSFWorkbook dealExcel(List<List<String>> list, String title) {

        if (deptMap == null) {
            deptMap = new HashMap<String, ExeDept>();
        }

        List<Integer> columnWidth = new ArrayList<Integer>();
        List<String> columnTitle = new ArrayList<String>();

        int data_len = list.size();

        // 读取部门名称列
        String dept_name = "部门名称";
        String dept_split = "\\.";
        // 部门所在列
        int dept_col = 0;
        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        for (int i = 0; i < col_len; i++) {
            if (dept_name.equals(colNames.get(i).trim())) {
                dept_col = i;
            }
            columnTitle.add(colNames.get(i));
            columnWidth.add(15);
        }

        // 部门最大深度
        int dept_len_max = 1;
        String dept = null;
        int dept_current_len = 1;
        for (int i = 1; i < data_len; i++) {
            dept = list.get(i).get(dept_col);
            dept_current_len = dept.split(dept_split).length;
            if (dept_len_max < dept_current_len) {
                dept_len_max = dept_current_len;
            }
        }
        for (int i = 1; i <= dept_len_max; i++) {
            columnTitle.add(String.format("部门%s", i));
            columnWidth.add(15);
        }
        // 增加4列：门店|门店类型|渠道类型|租赁类型
        columnTitle.add("门店");
        columnWidth.add(15);
        columnTitle.add("门店编码");
        columnWidth.add(15);
        columnTitle.add("门店类型");
        columnWidth.add(15);
        columnTitle.add("渠道类型");
        columnWidth.add(15);
        columnTitle.add("租赁类型");
        columnWidth.add(15);

        // 新数据：col_len + dept_len_max
        String[] newData = null;
        // 新数据，列数
        int col_len_new = col_len + dept_len_max;
        // 新数据集合
        List<String[]> datas = new ArrayList<String[]>();

        List<String> oldData = null;
        String oldDept = null;
        String[] oldDeptSplit = null;
        ExeDept ed = null;
        int oldDeptLen = 0;
        for (int i = 1; i < data_len; i++) {
            oldData = list.get(i);
            oldDept = oldData.get(dept_col);
            newData = new String[col_len_new + 5];
            int j = 0;
            for (; j < col_len; j++) {
                newData[j] = oldData.get(j);
            }
            // oldDeptSplit = oldDept.split(dept_split);
            // for (String s : oldDeptSplit) {
            // newData[j] = s;
            // ++j;
            // }
            oldDeptSplit = oldDept.split(dept_split);
            oldDeptLen = oldDeptSplit.length;
            for (int k = 0; k < dept_len_max; k++) {
                if (k < oldDeptLen) {
                    newData[j] = oldDeptSplit[k];
                } else {
                    newData[j] = "";
                }
                ++j;
            }

            // 增加4列：门店|门店类型|渠道类型|租赁类型
            if (deptMap.containsKey(oldDept)) {
                ed = deptMap.get(oldDept);
                newData[j++] = "是";
                newData[j++] = StringUtil.formateNull(ed.getShopsNo());
                newData[j++] = StringUtil.formateNull(ed.getShopsType());
                newData[j++] = StringUtil.formateNull(ed.getShopsQudao());
                newData[j++] = StringUtil.formateNull(ed.getShopsZulin());
            } else {
                newData[j++] = "否";
                newData[j++] = "";
                newData[j++] = "";
                newData[j++] = "";
                newData[j++] = "";
            }

            datas.add(newData);
        }
        return PoiUtil.export(title, datas, columnTitle);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/excel/xls.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView excel_xls(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {
                List<List<String>> list = PoiUtil.read_xls(file.getInputStream());
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    String title = file.getOriginalFilename();
                    title = title.substring(0, title.indexOf(".xls"));
                    log.info(String.format("[xls导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    deptMap = (Map<String, ExeDept>) request.getSession().getAttribute(key_exe_map_dept);
                    HSSFWorkbook workbook = dealExcel(list, title);
                    // log.info(title);
                    ViewExcel viewExcel = new ViewExcel();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("fileName", title);
                    try {
                        viewExcel.buildExcelDocument(params, workbook, request, response);
                    } catch (Exception e) {
                        log.error("[excel_xls]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return new ModelAndView(viewExcel, model);

                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/excel/csv.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView excel_csv(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {
                List<List<String>> list = PoiUtil.read_csv(file.getInputStream());
                if (list != null && !list.isEmpty()) {
                    deptMap = (Map<String, ExeDept>) request.getSession().getAttribute(key_exe_map_dept);
                    List<String> datas = dealCsv(list);
                    String title = file.getOriginalFilename();
                    title = title.substring(0, title.indexOf(".csv"));
                    log.info(String.format("[cvs导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    PrintWriter p = null;
                    try {
                        // 处理中文文件名
                        title = URLEncoder.encode(title, "UTF-8");
                        response.setContentType("application/oct-stream");
                        response.setHeader("Content-disposition", "attachment;filename=" + title + ".csv");
                        p = response.getWriter();
                        if (list != null && !list.isEmpty()) {
                            for (String data : datas) {
                                p.append(data).append("\r");
                            }
                        }
                    } catch (Exception e) {
                        log.error("[excel_xls]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return null;
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    private List<String> dealCsv(List<List<String>> list) {

        if (deptMap == null) {
            deptMap = new HashMap<String, ExeDept>();
        }

        // 新数据集合
        List<String> datas = new ArrayList<String>();

        List<String> columnTitle = new ArrayList<String>();

        int data_len = list.size();

        // 读取部门名称列
        String dept_name = "部门名称";
        String dept_split = "\\.";
        String csv_split = ",";
        // 部门所在列
        int dept_col = 0;
        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        for (int i = 0; i < col_len; i++) {
            if (dept_name.equals(colNames.get(i).trim())) {
                dept_col = i;
            }
            columnTitle.add(colNames.get(i));
        }

        // 部门最大深度
        int dept_len_max = 1;
        String dept = null;
        int dept_current_len = 1;
        for (int i = 1; i < data_len; i++) {
            dept = list.get(i).get(dept_col);
            dept_current_len = dept.split(dept_split).length;
            if (dept_len_max < dept_current_len) {
                dept_len_max = dept_current_len;
            }
        }
        for (int i = 1; i <= dept_len_max; i++) {
            columnTitle.add(String.format("部门%s", i));
        }
        // 增加4列：门店|门店类型|渠道类型|租赁类型
        columnTitle.add("门店");
        columnTitle.add("门店类型");
        columnTitle.add("门店编码");
        columnTitle.add("渠道类型");
        columnTitle.add("租赁类型");

        // 新数据：col_len + dept_len_max
        StringBuffer newData = new StringBuffer(100);
        // 新数据，列数
        int col_len_new = col_len + dept_len_max;

        // 增加标题行
        for (String s : columnTitle) {
            newData.append(s).append(csv_split);
        }
        newData.deleteCharAt(newData.length() - 1);
        datas.add(newData.toString());

        List<String> oldData = null;
        String oldDept = null;
        String[] oldDeptSplit = null;
        int oldDeptLen = 0;
        ExeDept ed = null;
        for (int i = 1; i < data_len; i++) {
            oldData = list.get(i);
            oldDept = oldData.get(dept_col);
            newData.delete(0, newData.length());
            int j = 0;
            for (; j < col_len; j++) {
                newData.append(oldData.get(j)).append(csv_split);
            }
            oldDeptSplit = oldDept.split(dept_split);
            oldDeptLen = oldDeptSplit.length;
            for (int k = 0; k < col_len_new - j; k++) {
                if (k < oldDeptLen) {
                    newData.append(oldDeptSplit[k]).append(csv_split);
                } else {
                    newData.append("").append(csv_split);
                }
            }
            // 增加4列：门店|门店类型|渠道类型|租赁类型
            if (deptMap.containsKey(oldDept)) {
                ed = deptMap.get(oldDept);
                newData.append("是").append(csv_split);
                newData.append(StringUtil.formateNull(ed.getShopsType())).append(csv_split);
                newData.append(StringUtil.formateNull(ed.getShopsNo())).append(csv_split);
                newData.append(StringUtil.formateNull(ed.getShopsQudao())).append(csv_split);
                newData.append(StringUtil.formateNull(ed.getShopsZulin())).append(csv_split);
            } else {
                newData.append("否").append(csv_split);
                newData.append("").append(csv_split);
                newData.append("").append(csv_split);
                newData.append("").append(csv_split);
                newData.append("").append(csv_split);
            }
            newData.deleteCharAt(newData.length() - 1);
            datas.add(newData.toString());
        }
        return datas;
    }

    @RequestMapping(value = "/import/dept.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> import_dept(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
                    list = PoiUtil.read_xlsx(file.getInputStream(), 0);
                } else if (title.endsWith(".csv")) {
                    // 执行 csv
                    title = title.substring(0, title.indexOf(".csv"));
                    list = PoiUtil.read_csv(file.getInputStream());
                }

                if (list != null && !list.isEmpty()) {
                    Map<String, ExeDept> map = read_dept(list);
                    request.getSession().setAttribute(key_exe_map_dept, map);
                    ret.put("d1", String.format("导入部门数【%s】", map.size()));
                    r1 = 200;
                    log.info(ret.get("d1"));
                }
            } catch (IOException e) {
            }
        }
        ret.put("r1", r1);
        return ret;
    }

    private Map<String, ExeDept> read_dept(List<List<String>> list) {
        Map<String, ExeDept> map = new HashMap<String, ExeDept>();
        int data_len = list.size();
        // 需要读取的列
        Set<String> needReadColSet = new HashSet<String>();
        needReadColSet.add("部门编号");
        needReadColSet.add("部门名称");
        needReadColSet.add("门店");
        // 所在列：部门编号
        int index_deptNo = -1;
        String value_deptNo = null;
        // 所在列：部门名称
        int index_deptName = -1;
        String value_deptName = null;
        // 所在列：门店
        int index_deptIsShops = -1;
        String value_deptIsShops = null;

        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        String value = null;
        for (int i = 0; i < col_len; i++) {
            value = colNames.get(i).trim();
            if ("部门编号".equals(value)) {
                index_deptNo = i;
            } else if ("部门名称".equals(value)) {
                index_deptName = i;
            } else if ("门店".equals(value)) {
                index_deptIsShops = i;
            }
        }

        // 读取数据
        List<String> rowData = null;
        int rowDataLen = 0;
        ExeDept ed = null;
        for (int i = 1; i < data_len; i++) {

            rowData = list.get(i);
            rowDataLen = rowData.size();
            if (index_deptNo > -1 && index_deptNo < rowDataLen) {
                value_deptNo = rowData.get(index_deptNo);
            }
            if (index_deptName > -1 && index_deptName < rowDataLen) {
                value_deptName = rowData.get(index_deptName);
            }
            if (index_deptIsShops > -1 && index_deptIsShops < rowDataLen) {
                value_deptIsShops = rowData.get(index_deptIsShops);
            }
            if (StringUtil.isNotEmpty(value_deptName) && "是".equals(value_deptIsShops)) {
                value_deptName = value_deptName.trim();
                ed = new ExeDept(value_deptNo, value_deptName, value_deptIsShops);
                map.put(value_deptName, ed);
                // log.info(ed.toString());
            }

            // release resource
            value_deptNo = null;
            value_deptName = null;
            value_deptIsShops = null;
            ed = null;
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/import/shops.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> import_shops(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
                    list = PoiUtil.read_xlsx(file.getInputStream(), 0);
                } else if (title.endsWith(".csv")) {
                    // 执行 csv
                    title = title.substring(0, title.indexOf(".csv"));
                    list = PoiUtil.read_csv(file.getInputStream());
                }

                deptMap = (Map<String, ExeDept>) request.getSession().getAttribute(key_exe_map_dept);

                if (deptMap == null || deptMap.isEmpty()) {
                    ret.put("r1", 201);
                    ret.put("d1", "没有部门信息，请先导入部门信息！");
                    return ret;
                }

                if (list != null && !list.isEmpty()) {
                    Map<String, ExeDept> map = read_shops(list);
                    request.getSession().setAttribute(key_exe_map_dept, map);
                    ret.put("d1", String.format("导入门店数【%s】", map.size()));
                    r1 = 200;
                    log.info(ret.get("d1"));
                }
            } catch (IOException e) {
            }
        }
        ret.put("r1", r1);
        return ret;
    }

    private Map<String, ExeDept> read_shops(List<List<String>> list) {
        Map<String, ExeDept> map = new HashMap<String, ExeDept>();
        if (deptMap == null || deptMap.isEmpty()) {
            return map;
        }
        int data_len = list.size();
        // 需要读取的列
        String col_shopsNo = "门店编码";
        String col_deptName = "归属部门";
        String col_shopsType = "门店类型";
        String col_shopsQudao = "渠道类型";
        String col_shopsZulin = "租赁类型";
        // 所在列：门店编码
        int index_shopsNo = -1;
        String value_shopsNo = null;
        // 所在列：归属部门
        int index_deptName = -1;
        String value_deptName = null;
        // 所在列：门店类型
        int index_shopsType = -1;
        String value_shopsType = null;
        // 所在列：渠道类型
        int index_shopsQudao = -1;
        String value_shopsQudao = null;
        // 所在列：租赁类型
        int index_shopsZulin = -1;
        String value_shopsZulin = null;

        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        String value = null;
        for (int i = 0; i < col_len; i++) {
            value = colNames.get(i).trim();
            if (col_shopsNo.equals(value)) {
                index_shopsNo = i;
            } else if (col_deptName.equals(value)) {
                index_deptName = i;
            } else if (col_shopsType.equals(value)) {
                index_shopsType = i;
            } else if (col_shopsQudao.equals(value)) {
                index_shopsQudao = i;
            } else if (col_shopsZulin.equals(value)) {
                index_shopsZulin = i;
            }
        }

        // 读取数据
        List<String> rowData = null;
        int rowDataLen = 0;
        ExeDept ed = null;
        for (int i = 1; i < data_len; i++) {
            rowData = list.get(i);
            rowDataLen = rowData.size();
            if (index_deptName > -1 && index_deptName < rowDataLen) {
                value_deptName = rowData.get(index_deptName);
            }
            if (StringUtil.isEmpty(value_deptName)) {
                continue;
            }
            value_deptName = value_deptName.trim();
            if (!deptMap.containsKey(value_deptName)) {
                continue;
            }
            ed = deptMap.get(value_deptName);
            if (index_shopsNo > -1 && index_shopsNo < rowDataLen) {
                value_shopsNo = rowData.get(index_shopsNo);
                if (StringUtil.isNotEmpty(value_shopsNo)) {
                    ed.setShopsNo(value_shopsNo.trim());
                }
            }
            if (index_shopsType > -1 && index_shopsType < rowDataLen) {
                value_shopsType = rowData.get(index_shopsType);
                if (StringUtil.isNotEmpty(value_shopsType)) {
                    ed.setShopsType(value_shopsType.trim());
                }
            }
            if (index_shopsQudao > -1 && index_shopsQudao < rowDataLen) {
                value_shopsQudao = rowData.get(index_shopsQudao);
                if (StringUtil.isNotEmpty(value_shopsQudao)) {
                    ed.setShopsQudao(value_shopsQudao.trim());
                }
            }
            if (index_shopsZulin > -1 && index_shopsZulin < rowDataLen) {
                value_shopsZulin = rowData.get(index_shopsZulin);
                if (StringUtil.isNotEmpty(value_shopsZulin)) {
                    ed.setShopsZulin(value_shopsZulin.trim());
                }
            }
            map.put(value_deptName, ed);

            // release resource
            value_deptName = null;
            value_shopsNo = null;
            value_shopsType = null;
            value_shopsQudao = null;
            value_shopsZulin = null;
            ed = null;
        }

        return map;
    }

    @RequestMapping(value = "/sw/import/dept.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> sw_import_dept(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
                    list = PoiUtil.read_xlsx(file.getInputStream(), 1);
                }

                if (list != null && !list.isEmpty()) {
                    Map<String, SwDept> map = read_dept_sw(list);
                    request.getSession().setAttribute(key_exe_map_dept_sw, map);
                    request.getSession().setAttribute(key_exe_map_dept_len_sw, pathLength);
                    ret.put("d1", String.format("导入部门数【%s】", map.size()));
                    r1 = 200;
                    log.info(ret.get("d1"));
                }
            } catch (IOException e) {
            }
        }
        ret.put("r1", r1);
        return ret;
    }

    private Map<String, SwDept> read_dept_sw(List<List<String>> list) {
        SwDept sd = null;
        if (swDeptMap == null) {
            swDeptMap = new HashMap<String, SwDept>();
        }
        if (list != null && !list.isEmpty()) {
            for (List<String> s : list) {
                if (s.size() < 4) {
                    log.error(String.format("[非法数据], [%s], [%s]", s.get(0), s.get(1)));
                    continue;
                }
                sd = new SwDept(s.get(0), s.get(1));
                if (StringUtil.isNotEmpty(s.get(2))) {
                    sd.setParentDeptNo(s.get(2));
                }
                swDeptMap.put(sd.getDeptNo(), sd);
            }
            formatDept_sw();
        }

        return swDeptMap;
    }

    private void formatDept_sw() {
        Map<String, SwDept> map = new HashMap<String, SwDept>();
        SwDept sd = null;
        int level = 0;
        String path = null;
        SwDept parent = null;
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
    @RequestMapping(value = "/sw/excel/xlsx.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView sw_excel_xlsx(@RequestParam("file") MultipartFile file, String deptNoColumnName, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {

                List<List<String>> list = new ArrayList<List<String>>();
                // 总条数
                String title = file.getOriginalFilename();
                if (title.endsWith(".xlsx")) {
                    // 执行 xlsx
                    title = title.substring(0, title.indexOf(".xlsx"));
                    list = PoiUtil.read_xlsx(file.getInputStream(), 0);
                }
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    log.info(String.format("[sw_excel_xlsx导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    swDeptMap = (Map<String, SwDept>) request.getSession().getAttribute(key_exe_map_dept_sw);
                    pathLength = (Integer) request.getSession().getAttribute(key_exe_map_dept_len_sw);
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
        log.info(String.format("部门编号所在列索引值=[%s]", deptNoColumnIndex));

        for (int j = 1; j <= pathLength; j++) {
            columnTitle.add(String.format("部门%s", j));
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
        for (int i = 1; i < size; i++) {
            // 序号 部门 部门名称
            // 职位 岗位 人员 姓名 有效状态 最近登录时间 累计启动次数 日均启动次数 累计在线时长 日均在线时长 页面活跃量
            // 单次页面访问量
            temp = list.get(i);
            if (deptNoColumnIndex < 0 || !swDeptMap.containsKey(temp.get(deptNoColumnIndex))) {
                // 没有部门数据
                datas.add(temp);
                System.out.println("error:" + temp);
                continue;
            }
            // 有部门数据
            path = swDeptMap.get(temp.get(deptNoColumnIndex)).getPath();
            paths = path.split("\\.");
            for (String p : paths) {
                temp.add(swDeptMap.get(p).getDeptName());
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

    /**
     * 
     * @date 2019年4月8日 下午4:10:39
     * 
     * @author swf
     * 
     * @Description 点微-银行账单
     * 
     * @param file
     * @param model
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dm/excel/xlsx.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView dm_excel_xlsx(@RequestParam("file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {

                List<List<String>> list = new ArrayList<List<String>>();
                // 总条数
                String title = file.getOriginalFilename();
                if (title.endsWith(".xlsx")) {
                    // 执行 xlsx
                    title = title.substring(0, title.indexOf(".xlsx"));
                    list = PoiUtil.read_xlsx_multiple_sheets(file.getInputStream(), 2, 6);
                }
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    log.info(String.format("[dm_excel_xlsx导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    HSSFWorkbook workbook = dealExcelDm(list, title);
                    ViewExcel viewExcel = new ViewExcel();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("fileName", title);
                    try {
                        viewExcel.buildExcelDocument(params, workbook, request, response);
                    } catch (Exception e) {
                        log.error("[dm_excel_xlsx]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return new ModelAndView(viewExcel, model);

                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @date 2019年4月8日 下午4:13:02
     * 
     * @author swf
     * 
     * @Description 日期排序
     * 
     * @param list
     * @param title
     * @return
     */
    private HSSFWorkbook dealExcelDm(List<List<String>> list, String title) {

        List<Integer> columnWidth = new ArrayList<Integer>();
        // 名字 日期 金额（元） 交易银行 摘要 颜色
        List<String> columnTitle = Arrays.asList(new String[] { "名字", "日期", " 金额（元）", "交易银行", "摘要", "颜色" });
        int col_len = 6;
        for (int i = 0; i < col_len; i++) {
            columnWidth.add(15);
        }
        // 新数据集合
        List<String[]> datasWrite = new ArrayList<String[]>();
        String[] newData = null;
        Date d = null;
        for (List<String> s : list) {
            if (s.size() < 3) {
                continue;
            }
            if (StringUtil.isEmpty(s.get(0)) || StringUtil.isEmpty(s.get(1))) {
                continue;
            }
            newData = new String[col_len];
            for (int i = 0; i < col_len; i++) {
                if (i < s.size()) {
                    newData[i] = s.get(i);
                    if (i == 1) {
                        // 日期，格式化
                        d = DateUtil.str2date(newData[i], 1);
                        if (d != null) {
                            newData[i] = DateUtil.dateToString(d, "yyyy-MM-dd");
                        }
                    }
                } else {
                    newData[i] = "";
                }
            }
            datasWrite.add(newData);
        }

        return PoiUtil.export(title, datasWrite, columnTitle);
    }

    /**
     * 
     * @date 2020年10月17日08:39:01
     * 
     * @author swf
     * 
     * @Description
     * 
     * @param file
     * @param dateColumnName
     *            日期所在列名称
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/jf/excel/xlsx.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView jf_excel_xlsx(@RequestParam("file") MultipartFile file, String dateColumnName, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (file != null && file.getSize() > 0) {
            try {

                List<List<String>> list = new ArrayList<List<String>>();
                // 总条数
                String title = file.getOriginalFilename();
                if (title.endsWith(".xlsx")) {
                    // 执行 xlsx
                    title = title.substring(0, title.indexOf(".xlsx"));
                    list = PoiUtil.read_xlsx(file.getInputStream(), 0);
                }
                if (list != null && !list.isEmpty()) {
                    // 总条数
                    log.info(String.format("[jf_excel_xlsx导入], 记录数=[%s], 文件名=[%s].", list.size(), title));
                    HSSFWorkbook workbook = dealExcelJf(list, title, dateColumnName);
                    ViewExcel viewExcel = new ViewExcel();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("fileName", title);
                    try {
                        viewExcel.buildExcelDocument(params, workbook, request, response);
                    } catch (Exception e) {
                        log.error("[jf_excel_xlsx]导出异常：" + e.getMessage(), e.getCause());
                    }
                    return new ModelAndView(viewExcel, model);

                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    private HSSFWorkbook dealExcelJf(List<List<String>> list, String title, String dateColumnName) {

        List<Integer> columnWidth = new ArrayList<Integer>();
        List<String> columnTitle = new ArrayList<String>();

        List<String> colNames = list.get(0);
        int col_len = colNames.size();
        // 部门编号所在列索引值，2019年4月3日12:26:28
        int dateStartColumnIndex = -1;
        int dateEndColumnIndex = -1;
        String dateStartColumnName = "计划开始日期";
        String dateEndColumnName = "计划结束日期";
        if (StringUtil.isNotEmpty(dateColumnName)) {
            dateColumnName = dateColumnName.trim().replaceAll("，", ",").replaceAll("、", ",");
            String[] s = dateColumnName.split(",");
            if (s.length >= 2) {
                if (StringUtil.isNotEmpty(s[0])) {
                    dateStartColumnName = s[0].trim();
                }
                if (StringUtil.isNotEmpty(s[1])) {
                    dateEndColumnName = s[1].trim();
                }
            }
        }
        for (int i = 0; i < col_len; i++) {
            // log.info("i=" + i + ", name=" + colNames.get(i));
            columnTitle.add(colNames.get(i));
            columnWidth.add(15);
            if (dateStartColumnIndex == -1 && StringUtil.isNotEmpty(colNames.get(i)) && dateStartColumnName.equals(colNames.get(i).trim())) {
                dateStartColumnIndex = i;
            }
            if (dateEndColumnIndex == -1 && StringUtil.isNotEmpty(colNames.get(i)) && dateEndColumnName.equals(colNames.get(i).trim())) {
                dateEndColumnIndex = i;
            }
        }
        log.info(String.format("开始日期列索引值=[%s]，结束日期列索引值=[%s]", dateStartColumnIndex, dateEndColumnIndex));

        // 新数据集合
        List<String[]> datasWrite = new ArrayList<String[]>();
        String[] newData = null;

        int size = list.size();
        List<String> temp = null;
        String startDate = null;
        String endDate = null;
        for (int i = 1; i < size; i++) {
            // 序号 部门 部门名称
            // 职位 岗位 人员 姓名 有效状态 最近登录时间 累计启动次数 日均启动次数 累计在线时长 日均在线时长 页面活跃量
            // 单次页面访问量
            temp = list.get(i);
            if (dateStartColumnIndex >= 0 && dateEndColumnIndex >= 0) {
                // 处理结束日期
                startDate = temp.get(dateStartColumnIndex);
                if (StringUtil.isNotEmpty(startDate)) {
                    endDate = DateUtil.getDateForNextMonth(startDate);
                }

                temp.set(dateEndColumnIndex, endDate);
            }

            // 组装数据
            newData = new String[col_len];
            for (int j = 0; j < col_len; j++) {
                newData[j] = temp.get(j);
            }
            datasWrite.add(newData);

        }

        return PoiUtil.export(title, datasWrite, columnTitle);
    }
}
