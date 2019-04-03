package com.dianmic.dmutil.domain;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description excel export
 *
 */
public class ViewExcel extends AbstractExcelView {

    @Override
    public void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 设置下载时客户端Excel的名称
        String fileName = "export";
        if (map != null && map.get("fileName") != null) {
            fileName = String.valueOf(map.get("fileName"));
            // 处理中文文件名
            // fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");

        // String agent = request.getHeader("USER-AGENT").toLowerCase();
        // if (agent.contains("firefox")) {
        // response.setHeader("Content-disposition", "attachment;filename=" +
        // fileName + ".xls");
        // } else {
        // }

        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

}
