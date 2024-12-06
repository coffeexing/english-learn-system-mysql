package cn.example.demo.common.tools.file.office;

import cn.example.demo.common.tools.obj.DateAgeUtils;
import cn.example.demo.common.tools.obj.reflect.FieldAlias;
import cn.example.demo.common.tools.obj.reflect.FieldIgnore;
import cn.example.demo.common.tools.obj.reflect.FieldStartCount;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;

/**
 * <p>
 * Excel文件工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2021/5/19 17:05
 */
public class ExcelFileUtils {
    /**
     * <p>
     * Excel 文件转实体类
     * </P>
     *
     * @param sheet
     * @return
     */
    public static <T> List<T> excelFileToEntity(Class<T> objType, Sheet sheet) throws Exception {
        // 获取表头列名与其索引值
        Row tableTitle = sheet.getRow(0);
        Row tableColumns = sheet.getRow(1);
        Map<String, Integer> columnMap = new HashMap<>();
        tableColumns.cellIterator().forEachRemaining(cell -> columnMap.put(cell.getStringCellValue(), cell.getColumnIndex()));

        // 通过反射获取实体类声明的字段
        Field[] fields = objType.getDeclaredFields();
        // 校验文件格式合法性
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldAlias.class)) {
                Integer columnIndex = columnMap.get(field.getAnnotation(FieldAlias.class).value());
                if (columnIndex == null) {  // 有一个不符合，返回空，不再分析
                    return null;
                }
            }
        }
        // 表格数据按行遍历，每行转换成对应实体类，并存入 List 集合
        List<T> l = new ArrayList<>();
        Date initDate = DateAgeUtils.stringToDate("19000101", "yyyyMMdd");  // 基准日期，用于处理 Excel 中的日期类型（int）
        String doubleFormat = "^[+-]?(0|([1-9]\\d*))(\\.\\d+)?$";   // 数值型格式（浮点数）
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            T entity = objType.getConstructor().newInstance();
            // 字段注入对应的值
            for (Field field : fields) {
                if (field.isAnnotationPresent(FieldAlias.class)) {
                    Integer columnIndex = columnMap.get(field.getAnnotation(FieldAlias.class).value());
                    Cell cell = r.getCell(columnIndex, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    // 初始化表格字段值
                    Object cellStringValue = null;
                    // 对不同数据类型的处理
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            double value = cell.getNumericCellValue();
                            // 若日期类型被识别为数值类型，则特殊处理
                            if (field.getAnnotation(FieldAlias.class).type().equals("Date")) {
                                // 123.0 转为 123 （去除'.0'）
                                NumberFormat nf = NumberFormat.getNumberInstance();
                                String s = nf.format(value);
                                if (s.indexOf(",") >= 0) {  // 科学计数法出现的',' 要去除
                                    s = s.replaceAll(",", "");
                                }
                                // 日期类型（数值型）两种情况
                                if (value > 190001) {   // yyyyMM 格式
                                    cellStringValue = DateAgeUtils.dateToString(DateAgeUtils.stringToDate(s), "yyyyMM");
                                } else {    // 距离【1900年1月1号】的天数
                                    Date d = DateAgeUtils.addDayAmountOfDate(initDate, Integer.parseInt(s) - 2);
                                    cellStringValue = DateAgeUtils.dateToString(d, "yyyyMM");
                                }
                            } else {
                                cellStringValue = BigDecimal.valueOf(value);
                            }
                            break;
                        case STRING:
                            String s = cell.getStringCellValue();
                            cellStringValue = s;
                            // 数值型字段
                            if (field.getAnnotation(FieldAlias.class).type().equals("BigDecimal")) {
                                if (!s.matches(doubleFormat)) {
                                    continue;
                                }
                                cellStringValue = new BigDecimal(s);
                            }
                            break;
                        case _NONE:
                        case BLANK:
                            continue;
                    }
                    // 赋值
                    field.setAccessible(true);
                    field.set(entity, cellStringValue);
                    field.setAccessible(false);
                }
            }
            // 存入集合
            l.add(entity);
        }
        return l;
    }

    /**
     * <p>
     * 导出表格。生成模板、并填充数据，返回 Workbook 对象字节流
     * </P>
     *
     * @param objType
     * @param originData
     * @param columns
     * @param sheetName
     * @param titleName
     * @param isAggregateRow（是否生成汇总行）
     * @param <T>
     * @return
     */
    public static <T> byte[] exportAsSheet(Class<T> objType, List<T> originData, Map<Integer, String> columns, String sheetName, String titleName, boolean isAggregateRow) throws Exception {
        return generateSheet(objType, originData, columns, sheetName, titleName, isAggregateRow);
    }

    /**
     * <p>
     * 导出表格。生成模板、并填充数据，返回 Workbook 对象字节流
     * </P>
     *
     * @param objType
     * @param originData
     * @param sheetName
     * @param titleName
     * @param isAggregateRow（是否生成汇总行）
     * @param <T>
     * @return
     */
    public static <T> byte[] exportAsSheet(Class<T> objType, List<T> originData, String sheetName, String titleName, boolean isAggregateRow) throws Exception {
        return generateSheet(objType, originData, generateColumns(objType), sheetName, titleName, isAggregateRow);
    }

    /**
     * <P>生成表格</P>
     *
     * @return
     */
    protected static <T> byte[] generateSheet(Class<T> objType, List<T> originData, Map<Integer, String> columns, String sheetName, String titleName, boolean isAggregateRow) throws Exception {
        // Get sheet
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet(sheetName);
        // 第1行标题合并为一个单元格
        if (columns.size() > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.size() - 1));
        }
        // 1.Set All of Title【表格头】
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(30f); // 【表格头】行高
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(getTitleStyle(workbook));
        titleCell.setCellValue(titleName);
        // 2.Set All of Column【表格列】&& Set All of Data 【表格行数据】
        generateColumnRowAndDataRow(workbook, sheet, columns, objType, originData);
        // 3.生成汇总行
        if (isAggregateRow) {
            generateCountRow(workbook, sheet, columns, objType);
        }
        // 冻结窗口(列名行固定)
        sheet.createFreezePane(0, 2);

        // return 字节流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }

    /**
     * <p>
     * 根据实体类的字段注解{@FieldAlias}生成表格【列名】
     * </P>
     *
     * @param clazz
     * @return
     */
    protected static Map<Integer, String> generateColumns(Class clazz) {
        Map<Integer, String> columns = new TreeMap();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(FieldAlias.class)) {
                columns.put(i, fields[i].getAnnotation(FieldAlias.class).value());
            } else {
                if (fields[i].isAnnotationPresent(FieldIgnore.class)) {
                    continue;
                }
                columns.put(i, fields[i].getName());
            }
        }
        return columns;
    }

    /**
     * <p>
     * 生成列名行和数据行
     * </P>
     *
     * @return
     */
    @SneakyThrows
    protected static <T> void generateColumnRowAndDataRow(Workbook workbook, Sheet sheet, Map<Integer, String> columns, Class objType, List<T> originData) {
        // 1.Set All of Column【表格列】
        Row columnRow = sheet.createRow(1);
        columnRow.setHeightInPoints(18.8f); // 【表格列】行高
        // 矩阵：保存列名行、数据行的所有单元格字符长度
        int[][] cellSizeMatrix = new int[columns.size()][originData.size() + 1];
        int cIndex = 0;
        for (Integer k : columns.keySet()) {
            Cell c = columnRow.createCell(cIndex, CellType.STRING);
            c.setCellStyle(getColumnStyle(workbook));    // Set Style
            c.setCellValue(columns.get(k)); // Set column name
            // 将所有列名长度保存到矩阵的第1行
            cellSizeMatrix[cIndex][0] = columns.get(k).getBytes(StandardCharsets.UTF_8).length;
            ++cIndex;
        }
        // 2.Set All of Data 【表格行数据】
        CellStyle dataRowStyle = getDataRowStyle(workbook); // 普通类型数据行样式

        CellStyle dataRowStyleOfPercentage = workbook.createCellStyle(); // 百分比类型数据行样式
        dataRowStyleOfPercentage.cloneStyleFrom(dataRowStyle);
        dataRowStyleOfPercentage.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle dataRowStyleOfDigit = workbook.createCellStyle(); // 数值类型数据行样式
        dataRowStyleOfDigit.cloneStyleFrom(dataRowStyleOfPercentage);
        dataRowStyleOfDigit.setAlignment(HorizontalAlignment.RIGHT);
        dataRowStyleOfDigit.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));   //  货币格式

        Field[] fields = objType.getDeclaredFields();
        // 3.数据填充、动态行高设置、所有单元格长度保存到矩阵
        for (int i = 0; i < originData.size(); i++) {   // 遍历所有行
            Row r = sheet.createRow(i + 2);
            r.setHeightInPoints(18); // 默认行高
            int j = 0;
            for (Integer k : columns.keySet()) {    // 遍历所有列
                fields[k].setAccessible(true);
                Object o = fields[k].get(originData.get(i));
                fields[k].setAccessible(false);
                // 默认样式
                Cell c = r.createCell(j++);
                c.setCellStyle(dataRowStyle);
                if (o == null) {    // 若字段值为空，不作赋值处理
                    continue;
                }

                String objStrValue = o.toString();  // 对象默认值
                // 行高：根据最大的列自适应
                int height = objStrValue.length();
                if (height > 18 && height < 150) {
                    r.setHeightInPoints(height);
                } else if (height > 150) {
                    r.setHeightInPoints(150);
                }

                // 内容长度
                int width = objStrValue.getBytes("GBK").length + 1;
                // 根据不同类型赋值和调整样式
                if (o instanceof BigDecimal) {
                    c.setCellValue(((BigDecimal) o).doubleValue());
                    c.setCellStyle(dataRowStyleOfDigit);
                } else if (o instanceof Integer) {
                    c.setCellValue((Integer) o);
                    c.setCellStyle(dataRowStyleOfDigit);
                } else if (o instanceof Date) {
                    String s = DateAgeUtils.dateToString((Date) o, "yyyy-MM-dd");
                    c.setCellValue(s);
                    width = s.length(); // 日期类型列宽特殊处理
                } else {
                    c.setCellValue(objStrValue);
                    // 百分比格式靠右对齐
                    String percentagePattern = "^([0-9.]+)[ ]*%";
                    if (objStrValue.matches(percentagePattern)) {
                        c.setCellStyle(dataRowStyleOfPercentage);
                    }
                }
                // 保存每单元格长度到矩阵，从第2行开始。（ps:因前面<j++>运算，所有这里要<j-1>）
                cellSizeMatrix[j - 1][i + 1] = width;
            }
        }
        // 4.根据每列最大值设置列宽
        for (int i = 0; i < cellSizeMatrix.length; i++) {
            // 每维数组排序并取最大值
            Arrays.sort(cellSizeMatrix[i]);
            int max = cellSizeMatrix[i][cellSizeMatrix[i].length - 1];
            // 乘上256代表1字节
            if (max > 118) {
                sheet.setColumnWidth(i, 118 * 256);
            } else if (max < 18) {
                sheet.setColumnWidth(i, 18 * 256);
            } else {
                sheet.setColumnWidth(i, max * 256);
            }
        }
    }

    /**
     * <p>
     * 生成汇总行
     * </P>
     *
     * @return
     */
    protected static void generateCountRow(Workbook workbook, Sheet sheet, Map<Integer, String> columns, Class objType) {
        CellStyle countRowStyle = getCountRowStyle(workbook);
        CellStyle countRowFirstColumnStyle = workbook.createCellStyle();
        countRowFirstColumnStyle.cloneStyleFrom(countRowStyle);
        countRowFirstColumnStyle.setAlignment(HorizontalAlignment.LEFT);

        int lastPhysicalRow = sheet.getLastRowNum() + 1;
        Row countRow = sheet.createRow(lastPhysicalRow);
        countRow.setHeightInPoints(30f);

        Cell cell = countRow.createCell(0, CellType.STRING);
        cell.setCellStyle(countRowFirstColumnStyle);
        cell.setCellValue("合计");

        countRowStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));   //  货币格式

        // 其他实体类按注解识别是否开启合计
        int cci = 0;
        Field[] fields = objType.getDeclaredFields();
        for (Integer k : columns.keySet()) {
            if (fields[k].isAnnotationPresent(FieldStartCount.class)) {
                Cell c = countRow.createCell(cci);
                c.setCellStyle(countRowStyle);
                String s = CellReference.convertNumToColString(cci);
                // 设置单元格求和公式
                c.setCellFormula("SUM(" + s + "3:" + s + lastPhysicalRow + ")");
            }
            cci++;
        }
    }

    /**
     * <p>
     * 生成【标题样式】
     * </P>
     *
     * @param workbook
     * @return
     */
    protected static CellStyle getTitleStyle(Workbook workbook) {
        CellStyle columnStyle = workbook.createCellStyle();
        // 字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 18);
        columnStyle.setFont(font);
        // 文本排列
        columnStyle.setAlignment(HorizontalAlignment.CENTER);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return columnStyle;
    }

    /**
     * <p>
     * 生成【列名样式】
     * </P>
     *
     * @param workbook
     * @return
     */
    protected static CellStyle getColumnStyle(Workbook workbook) {
        CellStyle columnStyle = workbook.createCellStyle();
        // 字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        columnStyle.setFont(font);
        // 文本排列
        columnStyle.setAlignment(HorizontalAlignment.CENTER);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 边框
        columnStyle.setBorderTop(BorderStyle.DOUBLE);
        columnStyle.setBorderRight(BorderStyle.THICK);
        columnStyle.setBorderBottom(BorderStyle.DOUBLE);
        columnStyle.setBorderLeft(BorderStyle.THICK);
        return columnStyle;
    }

    /**
     * <p>
     * 生成【数据行样式】
     * </P>
     *
     * @param workbook
     * @return
     */
    protected static CellStyle getDataRowStyle(Workbook workbook) {
        CellStyle columnStyle = workbook.createCellStyle();
        // 字体
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        columnStyle.setFont(font);
        // 文本排列
        columnStyle.setAlignment(HorizontalAlignment.LEFT);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 边框
        columnStyle.setBorderTop(BorderStyle.THIN);
        columnStyle.setBorderRight(BorderStyle.THIN);
        columnStyle.setBorderBottom(BorderStyle.THIN);
        columnStyle.setBorderLeft(BorderStyle.THIN);
        return columnStyle;
    }

    /**
     * <p>
     * 生成【汇总行样式】
     * </P>
     *
     * @param workbook
     * @return
     */
    protected static CellStyle getCountRowStyle(Workbook workbook) {
        CellStyle columnStyle = workbook.createCellStyle();
        // 字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        columnStyle.setFont(font);
        // 文本排列
        columnStyle.setAlignment(HorizontalAlignment.RIGHT);
        return columnStyle;
    }
}
