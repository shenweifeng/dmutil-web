package com.dianmic.dmutil.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description Excel工具类
 *
 */
public class PoiUtil {

    private static Log log = LogFactory.getLog(PoiUtil.class);

    public static List<List<String>> read_xls(InputStream in) {
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            if (in == null || in.available() < 1) {
                return list;
            }
            // 根据上述创建的输入流 创建工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook(in);
            // 得到第一页 sheet 页Sheet是从0开始索引的
            HSSFSheet sheet1 = wb.getSheetAt(0);
            int rows = sheet1.getLastRowNum();
            List<String> rowdatas = null;
            int rowstart = 0;
            int columnstart = 0;
            for (int i = rowstart; i <= rows; i++) {
                HSSFRow row = sheet1.getRow(i);
                if (row != null) {
                    rowdatas = new ArrayList<String>();
                    int cells = row.getLastCellNum();
                    for (int j = columnstart; j < cells; j++) {
                        HSSFCell cell = row.getCell(j);
                        // if (cell != null) {
                        // .getStringCellValue()
                        rowdatas.add(getCellFormatValue(cell));
                        // }
                    }
                    list.add(rowdatas);
                }
            }
            // 关闭输入流
            in.close();
        } catch (IOException e) {
            log.error("[read_xls] read error: " + e.getMessage(), e.getCause());
        }
        return list;
    }

    /**
     * 根据HSSFCell类型设置数据
     * 
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    // cellvalue = cell.getDateCellValue().toLocaleString();
                    // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) throws FileNotFoundException {

        String s = "2,0003000002    ,李宇雄,A030,集团.零售分销商.全国分销商.成都公司,总经理,分销商管理,有效,2017-09-15 08:42:29,,,0,0,,";

        if (s.endsWith(",")) {
            s += "???";
        }

        String[] s1 = s.split(",");

        System.out.println(s1.length);

        for (String s2 : s1) {
            if (StringUtil.isEmpty(s2)) {
                System.out.println("---");
            } else {
                System.out.println(s2);
            }
        }

        // BufferedReader reader = null;
        // try {
        // reader = new BufferedReader(new InputStreamReader(
        //
        // new FileInputStream(
        //
        // new File("D:\\test\\用户系统行为分析-02.csv")),
        //
        // "GBK"));
        //
        // // 第一行信息，为标题信息，不用,如果需要，注释掉
        // String line = null;
        // line = reader.readLine();
        // System.out.println(line);
        //
        // String s1 = null;
        //
        // s1 = new String(line.getBytes("ISO-8859-1"), charset);
        // System.out.println(s1);
        //
        // s1 = new String(line.getBytes("GBK"), charset);
        // System.out.println(s1);
        //
        // // 关闭输入流
        // reader.close();
        // } catch (IOException e) {
        // log.error("[read_csv] read error: " + e.getMessage(), e.getCause());
        // }

        // FileInputStream file = new FileInputStream(excel);
        // System.out.println(read(file, 1, 1).size());

        // String title = "福建省-厦门市-现金送达服务（" + StringUtil.getCurrentTime() + "）";
        // String exportFile = "D:\\test\\" + title + ".xls";
        // Map<String, List<String[]>> dataMap = new HashMap<String,
        // List<String[]>>();
        // List<String[]> datas = new ArrayList<String[]>();
        // for (int i = 1; i <= 5; i++) {
        // datas = new ArrayList<String[]>();
        // for (int j = 1; j <= 4; j++) {
        // datas.add(
        // new String[] { "营业款日期" + j, String.valueOf(1000 * j * i),
        // String.valueOf(1000 * j * i), "0" });
        // }
        // dataMap.put("CN01" + i, datas);
        // }
        // List<String> columnTitle = Arrays.asList(new String[] { "营业款日期",
        // "门店交接金额", "银行汇款金额", "差异金额" });
        // HSSFWorkbook workbook = exportMultipleSheet(title, columnTitle,
        // dataMap);
        // OutputStream out = null;
        // try {
        // out = new FileOutputStream(exportFile);
        // workbook.write(out);
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

    }

    /**
     * @Title: resultSetToExcel @Description: 根据结果集生成excel（自适应宽度） @param rs
     *         数据集 @param sheetName 工作表名称 @return HSSFWorkbook 返回类型 @throws
     */
    public static HSSFWorkbook export(String title, List<String[]> datas, List<String> columnTitle) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);

        if (datas == null || datas.isEmpty()) {
            return workbook;
        }

        int size = datas.size();
        int record = 2;
        int sheetNum = 1;

        // workbook.setSheetName(0,sheetName,HSSFWorkbook..ENCODING_UTF_16);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = null;
        int nColumn = datas.get(0).length;

        int[] columnWidths = new int[nColumn];
        int valueLength = 0;

        if (columnTitle != null && !columnTitle.isEmpty()) {
            // 写入各个字段的名称
            for (int i = 0; i < nColumn; i++) {
                cell = row.createCell(i);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(columnTitle.get(i));

                valueLength = cell.getStringCellValue().getBytes().length;
                if (valueLength > columnWidths[i]) {
                    columnWidths[i] = valueLength;
                }
            }
        }
        int iRow = 1;
        // 写入各条记录，每条记录对应Excel中的一行
        for (int i = 0; i < size; i++) {

            if (record % Constant.excel_sheet_max_records < 0) {
                sheet = workbook.createSheet(title + "(" + sheetNum + ")");
                row = sheet.createRow((short) 0);
                cell = null;
                nColumn = datas.get(0).length;
                if (columnTitle != null && !columnTitle.isEmpty()) {
                    // 写入各个字段的名称
                    for (int ii = 0; ii < nColumn; ii++) {
                        cell = row.createCell(ii);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(columnTitle.get(ii));

                        valueLength = cell.getStringCellValue().getBytes().length;
                        if (valueLength > columnWidths[i]) {
                            columnWidths[i] = valueLength;
                        }
                    }
                }
                iRow = 1;
                ++sheetNum;
            }

            row = sheet.createRow(iRow);
            int cellValue = 0;
            for (int j = 0; j < nColumn; j++) {
                cell = row.createCell(j);
                Object oj = datas.get(i)[j];
                if (oj == null) {
                    oj = "";
                }
                cellValue = NumberUtils.toInt(oj.toString(), -1);
                if (cellValue > -1) {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(cellValue);
                } else {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oj.toString());
                }
                valueLength = oj.toString().getBytes().length;
                if (valueLength > columnWidths[j]) {
                    columnWidths[j] = valueLength;
                }
            }
            iRow++;

            ++record;
        }

        // 调整列宽
        for (int i = 0; i < nColumn; i++) {
            // sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, columnWidths[i] * 256);
        }

        return workbook;
    }

    // private static HSSFFont createHSSFFont(HSSFWorkbook wb) {
    // // 创建字体样式
    // HSSFFont font = wb.createFont();
    // font.setFontName("微软雅黑");
    // // font.setBoldweight((short) 100);
    // // font.setFontHeight((short) 300);
    // font.setColor(HSSFColor.BLUE.index);
    // return font;
    // }

    private static HSSFFont createHSSFFontTitle(HSSFWorkbook wb) {
        // 创建字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBoldweight((short) 100);
        // font.setFontHeight((short) 300);
        font.setColor(HSSFColor.BLUE.index);
        return font;
    }

    private static HSSFFont createHSSFFontContent(HSSFWorkbook wb) {
        // 创建字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        // font.setBoldweight((short) 100);
        // font.setFontHeight((short) 300);
        font.setColor(HSSFColor.BLACK.index);
        return font;
    }

    private static HSSFCellStyle createStyleContent(HSSFWorkbook wb, HSSFFont font, boolean border) {
        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        if (border) {
            // 设置边框
            // style.setBottomBorderColor(HSSFColor.RED.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        }

        if (font == null) {
            font = createHSSFFontContent(wb);
        }
        style.setFont(font);// 设置字体

        return style;
    }

    private static HSSFCellStyle createStyleTitle(HSSFWorkbook wb) {
        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置边框
        // style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setFont(createHSSFFontTitle(wb));// 设置字体

        return style;
    }

    private static HSSFCellStyle createStyleContent(HSSFWorkbook wb) {
        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置边框
        // style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setFont(createHSSFFontContent(wb));// 设置字体

        return style;
    }

    /**
     * 
     * @date 2018年5月8日 上午10:43:16
     * 
     * @author swf
     * 
     * @Description 导出多sheet记录
     * 
     * @param title
     * @param columnTitle
     * @param dataMap
     *            key:sheetName value: datas
     * @return
     */
    public static HSSFWorkbook exportMultipleSheet(String title, List<String> columnTitle,
            Map<String, List<String[]>> dataMap) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        if (dataMap == null || dataMap.isEmpty()) {
            return workbook;
        }

        // sheet index from 0 start
        HSSFSheet sheet = null;
        List<String[]> datas = null;
        for (String s : dataMap.keySet()) {
            sheet = workbook.createSheet(s);
            datas = dataMap.get(s);
            int size = datas.size();
            // workbook.setSheetName(0,sheetName,HSSFWorkbook..ENCODING_UTF_16);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = null;
            int nColumn = datas.get(0).length;

            int[] columnWidths = new int[nColumn];
            int valueLength = 0;

            if (columnTitle != null && !columnTitle.isEmpty()) {
                // 写入各个字段的名称
                for (int i = 0; i < nColumn; i++) {
                    cell = row.createCell(i);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(columnTitle.get(i));

                    valueLength = cell.getStringCellValue().getBytes().length;
                    if (valueLength > columnWidths[i]) {
                        columnWidths[i] = valueLength;
                    }
                }
            }

            int iRow = 1;
            // 写入各条记录，每条记录对应Excel中的一行
            for (int i = 0; i < size; i++) {

                // if (record % Constant.excel_sheet_max_records == 0) {
                // sheet = workbook.createSheet(title + "(" + sheetNum + ")");
                // row = sheet.createRow((short) 0);
                // cell = null;
                // nColumn = datas.get(0).length;
                // if (columnTitle != null && !columnTitle.isEmpty()) {
                // // 写入各个字段的名称
                // for (int ii = 0; ii < nColumn; ii++) {
                // cell = row.createCell(ii);
                // cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                // cell.setCellValue(columnTitle.get(ii));
                // }
                // }
                // iRow = 1;
                // ++sheetNum;
                // }

                row = sheet.createRow(iRow);
                for (int j = 0; j < nColumn; j++) {
                    cell = row.createCell(j);
                    Object oj = datas.get(i)[j];
                    if (oj == null) {
                        oj = "";
                    }
                    if (j == 0) {
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(oj.toString());
                    } else {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(NumberUtils.toDouble(oj.toString(), 0));
                    }
                    valueLength = oj.toString().getBytes().length;
                    if (valueLength > columnWidths[j]) {
                        columnWidths[j] = valueLength;
                    }
                }
                iRow++;

            }

            // 调整列宽
            for (int i = 0; i < nColumn; i++) {
                sheet.autoSizeColumn((short) i);
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

        } // end of dataMap foreach

        return workbook;
    }

    public static HSSFWorkbook exportForReport(String title, List<String[]> datas, List<String> columnTitle) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);

        if (datas == null || datas.isEmpty()) {
            return workbook;
        }

        int size = datas.size();
        int record = 2;
        int sheetNum = 1;

        // workbook.setSheetName(0,sheetName,HSSFWorkbook..ENCODING_UTF_16);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = null;
        int nColumn = datas.get(0).length;

        int[] columnWidths = new int[nColumn];
        int valueLength = 0;

        if (columnTitle != null && !columnTitle.isEmpty()) {
            // 写入各个字段的名称
            for (int i = 0; i < nColumn; i++) {
                cell = row.createCell(i);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(columnTitle.get(i));

                valueLength = cell.getStringCellValue().getBytes().length;
                if (valueLength > columnWidths[i]) {
                    columnWidths[i] = valueLength;
                }
            }
        }

        int iRow = 1;
        // 写入各条记录，每条记录对应Excel中的一行
        for (int i = 0; i < size; i++) {

            if (record % Constant.excel_sheet_max_records == 0) {
                sheet = workbook.createSheet(title + "(" + sheetNum + ")");
                row = sheet.createRow((short) 0);
                cell = null;
                nColumn = datas.get(0).length;
                if (columnTitle != null && !columnTitle.isEmpty()) {
                    // 写入各个字段的名称
                    for (int ii = 0; ii < nColumn; ii++) {
                        cell = row.createCell(ii);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(columnTitle.get(ii));

                        valueLength = cell.getStringCellValue().getBytes().length;
                        if (valueLength > columnWidths[i]) {
                            columnWidths[i] = valueLength;
                        }
                    }
                }
                iRow = 1;
                ++sheetNum;
            }

            row = sheet.createRow(iRow);
            for (int j = 0; j < nColumn; j++) {
                cell = row.createCell(j);
                Object oj = datas.get(i)[j];
                if (oj == null) {
                    oj = "";
                }
                if (j == 1) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oj.toString());
                } else {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(NumberUtils.toDouble(oj.toString(), 0));
                }
                valueLength = oj.toString().getBytes().length;
                if (valueLength > columnWidths[j]) {
                    columnWidths[j] = valueLength;
                }

            }
            iRow++;

            ++record;
        }

        // 调整列宽
        for (int i = 0; i < nColumn; i++) {
            sheet.autoSizeColumn((short) i);
            // sheet.setColumnWidth(i, columnWidths[i] * 2 * 256);
        }

        return workbook;
    }

    // *************xlsx文件读取函数************************
    // 返回二维字符串数组
    @SuppressWarnings({ "unused" })
    public static List<List<String>> read_xlsx(InputStream is, int rowStart) {
        List<List<String>> ans = new ArrayList<List<String>>();
        try {
            if (is == null || is.available() < 1) {
                return ans;
            }

            // 读取xlsx文件
            XSSFWorkbook xssfWorkbook = null;
            // 寻找目录读取文件
            xssfWorkbook = new XSSFWorkbook(is);

            if (xssfWorkbook == null) {
                System.out.println("未读取到内容,请检查路径！");
                return null;
            }

            // 遍历xlsx中的sheet
            // for (int numSheet = 0; numSheet <
            // xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet == null) {
                System.out.println("sheet不存在！");
                return null;
            }
            // 获取总列数
            int col_len = xssfSheet.getRow(0).getPhysicalNumberOfCells();
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = rowStart; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null)
                    continue;
                ArrayList<String> curarr = new ArrayList<String>();
                for (int columnNum = 0; columnNum < col_len; columnNum++) {
                    XSSFCell cell = xssfRow.getCell(columnNum);

                    // curarr.add(Trim_str(getValue(cell)));
                    curarr.add(getValue(cell));
                }
                ans.add(curarr);
            }
            // }
        } catch (IOException e) {
            log.error("[read_xlsx] read error: " + e.getMessage(), e.getCause());
        }
        return ans;
    }

    // 判断后缀为xlsx的excel文件的数据类
    private static String getValue(XSSFCell xssfRow) {
        if (xssfRow == null) {
            return "";
        }
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            double cur = xssfRow.getNumericCellValue();
            long longVal = Math.round(cur);
            Object inputValue = null;
            if (Double.parseDouble(longVal + ".0") == cur)
                inputValue = longVal;
            else
                inputValue = cur;
            return String.valueOf(inputValue);
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BLANK
                || xssfRow.getCellType() == xssfRow.CELL_TYPE_ERROR) {
            return "";
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    public static List<List<String>> read_csv(InputStream in) {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader reader = null;
        try {
            if (in == null || in.available() < 1) {
                return list;
            }
            reader = new BufferedReader(new InputStreamReader(in, "GBK"));

            // 第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            line = reader.readLine();

            String csv_spilt = ",";

            List<String> rowdatas = new ArrayList<String>();

            for (String s : line.split(csv_spilt)) {
                rowdatas.add(s);
            }
            list.add(rowdatas);

            int num = 1;
            String endStrReplaceOfBlank = "???";
            while ((line = reader.readLine()) != null) {
                ++num;
                if (line.endsWith(csv_spilt)) {
                    line += endStrReplaceOfBlank;
                }
                String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
                rowdatas = new ArrayList<String>();
                for (String s : item) {
                    if (endStrReplaceOfBlank.equals(s)) {
                        s = "";
                    }
                    rowdatas.add(s);
                }
                list.add(rowdatas);
            }
            log.info(String.format("[read_csv], 共[%s]行.", num));

            // 关闭输入流
            in.close();
        } catch (IOException e) {
            log.error("[read_csv] read error: " + e.getMessage(), e.getCause());
        }

        return list;
    }
}
