package com.aienuo.agile.util.excel;

import com.aienuo.agile.annotation.Excel;
import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.system.model.entity.File;
import com.aienuo.agile.module.system.model.vo.DictItemVO;
import com.aienuo.agile.module.system.service.IDictItemService;
import com.aienuo.agile.module.system.service.IFileService;
import com.aienuo.agile.util.AgileUtil;
import com.aienuo.agile.util.FileUtil;
import com.aienuo.agile.util.JacksonUtils;
import com.aienuo.agile.util.SpringBeanUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel 相关处理工具类
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月20日 10:51
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelUtil<T> {

    /**
     * Excel Sheet 最大行数，默认65536
     */
    public static final int SHEET_SIZE = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Excel.Type type;

    /**
     * 工作薄对象
     */
    private Workbook workbook;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 当前行号
     */
    private int rowNumber;

    /**
     * 标题
     */
    private String title;

    /**
     * 最大高度
     */
    private short maxHeight;

    /**
     * 统计列表
     */
    private final Map<Integer, String> statistics = new HashMap<>();

    /**
     * 实体对象
     */
    private final Class<T> clazz;

    //********************************************************** Excel 文件 导出 Start **********************************************************/

    /**
     * 获取字段注解信息<br>
     * <p>
     * <li>Object[0] = Field</li>
     * <li>Object[1] = Excel</li>
     */
    private List<Object[]> getFieldList() {
        // 注解列表
        List<Object[]> fieldList = new ArrayList<>();
        // 字段列表
        List<Field> tempFieldList = new ArrayList<>();
        tempFieldList.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFieldList) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                Excel excelAnnotation = field.getAnnotation(Excel.class);
                if (excelAnnotation != null && (excelAnnotation.type() == Excel.Type.ALL || excelAnnotation.type() == type)) {
                    // 设置无障碍
                    field.setAccessible(true);
                    fieldList.add(new Object[]{field, excelAnnotation});
                }
            }
            // 多注解
            if (field.isAnnotationPresent(Excel.List.class)) {
                Excel.List excelAnnotationList = field.getAnnotation(Excel.List.class);
                Excel[] excelAnnotationArray = excelAnnotationList.value();
                for (Excel excelAnnotation : excelAnnotationArray) {
                    if (excelAnnotation != null && (excelAnnotation.type() == Excel.Type.ALL || excelAnnotation.type() == type)) {
                        // 设置无障碍
                        field.setAccessible(true);
                        fieldList.add(new Object[]{field, excelAnnotation});
                    }
                }
            }
        }
        return fieldList;
    }

    /**
     * 根据注解获取最大行高
     */
    private short getRowHeight() {
        double maxHeight = 0;
        for (Object[] os : this.fields) {
            Excel excelAnnotation = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excelAnnotation.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = this.getFieldList();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
        this.maxHeight = this.getRowHeight();
    }

    /**
     * 创建表格样式
     *
     * @param workbook - 工作薄对象
     * @return Map<String, CellStyle> - 样式列表
     */
    private Map<String, CellStyle> createStyles(final Workbook workbook) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styleHashMap = new HashMap<>(7);
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styleHashMap.put("title", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styleHashMap.put("data", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styleHashMap.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styleHashMap.put("header", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = workbook.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styleHashMap.put("total", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styleHashMap.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styleHashMap.put("data1", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styleHashMap.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styleHashMap.put("data2", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styleHashMap.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styleHashMap.put("data3", style);

        return styleHashMap;
    }

    /**
     * 创建一个工作簿
     */
    private void createWorkbook() {
        this.workbook = new SXSSFWorkbook(500);
        this.sheet = this.workbook.createSheet();
        this.workbook.setSheetName(0, this.sheetName);
        this.styles = this.createStyles(this.workbook);
    }

    /**
     * 创建 Excel 第一行标题
     */
    private void createTitle() {
        if (AgileUtil.isNotEmpty(this.title)) {
            Row titleRow = this.sheet.createRow(this.rowNumber == 0 ? this.rowNumber++ : 0);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(this.styles.get("title"));
            titleCell.setCellValue(this.title);
            this.sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), this.fields.size() - 1));
        }
    }

    /**
     * 初始化
     *
     * @param list      - 将要导出的数据
     * @param sheetName - 工作表名称
     * @param title     - 标题
     * @param type      - 类型（0：导出导入；1：仅导出；2：仅导入）
     */
    private void init(List<T> list, final String sheetName, final String title, final Excel.Type type) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        // 创建 Excel 字段
        this.createExcelField();
        // 创建工作簿
        this.createWorkbook();
        // 创建 Excel 标题
        this.createTitle();
    }

    /**
     * 创建工作表 Sheet
     *
     * @param sheetQuantity - Sheet 数量
     * @param sheetIndex    - Sheet 序号
     */
    private void createSheet(final int sheetQuantity, final int sheetIndex) {
        if (sheetQuantity > 1 && sheetIndex > 0) {
            // 1、创建工作表 Sheet
            this.sheet = this.workbook.createSheet();
            // 2、创建标题
            this.createTitle();
            // 3、设置工作表的名称
            this.workbook.setSheetName(sheetIndex, this.sheetName + sheetIndex);
        }
    }

    /**
     * 设置 POI XSSF Sheet 单元格提示
     *
     * @param sheet         - Sheet 工作表
     * @param promptTitle   - 提示标题
     * @param promptContent - 提示内容
     * @param firstRow      - 开始行
     * @param lastRow       - 结束行
     * @param firstCol      - 开始列
     * @param lastCol       - 结束列
     */
    private void setXSSFPrompt(final Sheet sheet, final String promptTitle, final String promptContent, final int firstRow, final int lastRow, final int firstCol, final int lastCol) {
        // Sheet 工作表 获取数据验证助手
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        // 创建自定义约束
        DataValidationConstraint dataValidationConstraint = dataValidationHelper.createCustomConstraint("DD1");
        // 设置数据有效性加载在哪个单元格上，（四个参数分别是：起始行、终止行、起始列、终止列）
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建验证
        DataValidation dataValidation = dataValidationHelper.createValidation(dataValidationConstraint, cellRangeAddressList);
        // 创建提示框
        dataValidation.createPromptBox(promptTitle, promptContent);
        // 设置显示提示框
        dataValidation.setShowPromptBox(true);
        // Sheet 工作表 添加验证数据
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置 POI XSSF Sheet 某些列的值只能输入预制的数据，显示下拉框
     *
     * @param sheet    - Sheet 工作表
     * @param textList - 下拉框显示的内容
     * @param firstRow - 开始行
     * @param lastRow  - 结束行
     * @param firstCol - 开始列
     * @param lastCol  - 结束列
     */
    private void setXSSFValidation(final Sheet sheet, final String[] textList, final int firstRow, final int lastRow, final int firstCol, final int lastCol) {
        // Sheet 工作表 获取数据验证助手
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint dataValidationConstraint = dataValidationHelper.createExplicitListConstraint(textList);
        // 设置数据有效性加载在哪个单元格上，（四个参数分别是：起始行、终止行、起始列、终止列）
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 数据有效性对象
        DataValidation dataValidation = dataValidationHelper.createValidation(dataValidationConstraint, cellRangeAddressList);
        // 处理 Excel 兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            // 设置抑制下拉箭头 - true
            dataValidation.setSuppressDropDownArrow(true);
            // 设置显示错误框 - true
            dataValidation.setShowErrorBox(true);
        } else {
            // 设置抑制下拉箭头 - false
            dataValidation.setSuppressDropDownArrow(false);
        }
        // Sheet 工作表 添加验证数据
        sheet.addValidationData(dataValidation);
    }

    /**
     * 创建表格样式
     *
     * @param excelAnnotation - Excel 注解
     * @param column          - 列
     */
    private void setDataStyle(final Excel excelAnnotation, final int column) {
        if (excelAnnotation.name().contains("注：")) {
            // 设置固定列宽（单位为字符）
            this.sheet.setColumnWidth(column, 6000);
        } else {
            // 根据 导出时在Excel中每个列的宽 计算列宽（单位为字符）
            this.sheet.setColumnWidth(column, (int) ((excelAnnotation.width() + 0.72) * 256));
        }
        // 如果设置了提示信息则鼠标放上去提示
        if (AgileUtil.isNotEmpty(excelAnnotation.prompt())) {
            // 这里默认设了 2-101 列提示
            this.setXSSFPrompt(sheet, "", excelAnnotation.prompt(), 1, 100, column, column);
        }
        // 如果设置了 combo 属性则本列只能选择不能输入
        if (excelAnnotation.combo().length > 0) {
            // 这里默认设了 2-101 列只能选择不能输入
            this.setXSSFValidation(sheet, excelAnnotation.combo(), 1, 100, column, column);
        }
    }

    /**
     * 创建单元格
     *
     * @param excelAnnotation - Excel 注解
     * @param row             - 行
     * @param column          - 列
     */
    private void createCell(final Excel excelAnnotation, final Row row, final int column) {
        // 1、创建列
        Cell cell = row.createCell(column);
        // 2、写入列信息
        cell.setCellValue(excelAnnotation.name());
        // 3、创建表格样式
        this.setDataStyle(excelAnnotation, column);
        // 4、设置单元格样式
        cell.setCellStyle(styles.get("header"));
    }

    /**
     * 以类的属性的 Get 方法 形式获取值
     *
     * @param fieldValue - 字段的值
     * @param fieldName  - 属性名称
     * @return Object - 字段的值
     * @throws Exception - 异常
     */
    private Object getValue(Object fieldValue, final String fieldName) throws Exception {
        if (AgileUtil.isNotEmpty(fieldValue) && AgileUtil.isNotEmpty(fieldName)) {
            Class<?> clazz = fieldValue.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            // 设置无障碍
            field.setAccessible(true);
            fieldValue = field.get(fieldValue);
        }
        return fieldValue;
    }

    /**
     * 获取 Bean 中的属性值
     *
     * @param exportObject    - 导出的实体对象
     * @param field           - 字段
     * @param excelAnnotation - 注解
     * @return Object - 最终的属性值
     * @throws Exception - 异常
     */
    private Object getTargetValue(final T exportObject, final Field field, final Excel excelAnnotation) throws Exception {
        // 返回指定对象上此 Field 表示的字段的值
        Object fieldValue = field.get(exportObject);
        // 另一个类中的属性名称，支持多级获取，以小数点隔开
        if (AgileUtil.isNotEmpty(excelAnnotation.targetAttr())) {
            String target = excelAnnotation.targetAttr();
            if (target.contains(StringPool.DOT)) {
                String regex = StringPool.LEFT_SQ_BRACKET + StringPool.DOT + StringPool.RIGHT_SQ_BRACKET;
                String[] targets = target.split(regex);
                for (String fieldName : targets) {
                    fieldValue = this.getValue(fieldValue, fieldName);
                }
            } else {
                fieldValue = this.getValue(fieldValue, target);
            }
        }
        return fieldValue;
    }

    /**
     * 解析导出值 <br>
     * 例如： 0=男,1=女,2=未知
     *
     * @param propertyValue       - 参数值
     * @param converterExpression - 翻译注解
     * @param separator           - 分隔符
     * @return String - 解析后值
     */
    private String convertByExpression(final String propertyValue, final String converterExpression, final String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExpression.split(StringPool.COMMA);
        for (String item : convertSource) {
            String[] itemArray = item.split(StringPool.EQUALS);
            if (propertyValue.contains(separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return AgileUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 解析字典值
     *
     * @param itemValue - 字典-项 值
     * @param dictCode  - 字典编码
     * @param separator - 分隔符
     * @return String - 字典项文本
     */
    private String convertDictByExpression(final String itemValue, final String dictCode, final String separator) {
        StringBuilder propertyString = new StringBuilder();
        // 字典 - 值 服务类
        IDictItemService dictItemService = SpringBeanUtils.getBean(IDictItemService.class);
        if (AgileUtil.isNotEmpty(dictItemService)) {
            // 字典 - 值 查询
            List<DictItemVO> dictItemList = dictItemService.queryDictItemListByDictCode(dictCode);
            if (itemValue.contains(separator) && AgileUtil.isNotEmpty(dictItemList)) {
                for (DictItemVO item : dictItemList) {
                    for (String value : itemValue.split(separator)) {
                        if (value.equals(item.getValue())) {
                            propertyString.append(item.getName()).append(separator);
                            break;
                        }
                    }
                }
            } else {
                for (DictItemVO item : dictItemList) {
                    if (itemValue.equals(item.getValue())) {
                        return item.getName();
                    }
                }
            }
        }
        return AgileUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 获取画布
     *
     * @param sheet - Sheet 工作表
     * @return Drawing<?> - 画布
     */
    private Drawing<?> getDrawingPatriarch(final Sheet sheet) {
        if (sheet.getDrawingPatriarch() == null) {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    /**
     * 读取文件为字节数据
     *
     * @param fileUrl - 图片地址
     * @return byte[] - 字节数据
     */
    private byte[] readFile(final String fileUrl) {
        InputStream inputStream = null;
        try {
            if (fileUrl.startsWith("http")) {
                // 网络地址
                URL urlObj = new URL(fileUrl);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                inputStream = urlConnection.getInputStream();
            } else {
                // 本机地址
                inputStream = Files.newInputStream(Paths.get(fileUrl));
            }
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("获取文件路径异常 {}", e.getMessage());
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 获取图像文件后缀名
     *
     * @param photoByte - 图片字节
     * @return String - 图像文件后缀名
     */
    private String getImageFileExtendName(final byte[] photoByte) {
        String imageFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
            imageFileExtendName = "gif";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
            imageFileExtendName = "jpg";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            imageFileExtendName = "bmp";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            imageFileExtendName = "png";
        }
        return imageFileExtendName;
    }

    /**
     * 获取图片类型，设置图片插入类型
     *
     * @param photoByte - 图片字节
     * @return int - 图片插入类型
     */
    private int getImageFileType(final byte[] photoByte) {
        String type = getImageFileExtendName(photoByte);
        if ("JPG".equalsIgnoreCase(type)) {
            // JPG
            return Workbook.PICTURE_TYPE_JPEG;
        } else if ("PNG".equalsIgnoreCase(type)) {
            // PNG
            return Workbook.PICTURE_TYPE_PNG;
        }
        // 默认 JPG
        return Workbook.PICTURE_TYPE_JPEG;
    }

    /**
     * 设置单元格内容
     *
     * @param fieldValue      - 单元格值
     * @param excelAnnotation - 注解相关
     * @param cell            - 单元格信息
     */
    private void setCellValue(final Object fieldValue, final Excel excelAnnotation, final Cell cell) {
        if (Excel.ColumnType.STRING == excelAnnotation.cellType()) {
            // 导出类型 - 字符串
            cell.setCellValue(AgileUtil.isEmpty(fieldValue) ? excelAnnotation.defaultValue() : fieldValue + excelAnnotation.suffix());
        } else if (Excel.ColumnType.NUMERIC == excelAnnotation.cellType()) {
            // 导出类型 - 数字
            if (AgileUtil.isNumeric(fieldValue)) {
                String stringValue = fieldValue.toString();
                cell.setCellValue(stringValue.contains(StringPool.DOT) ? Double.parseDouble(stringValue.trim()) : Integer.parseInt(stringValue.trim()));
            }
        } else if (Excel.ColumnType.IMAGE == excelAnnotation.cellType()) {
            // 导出类型 - 图片
            if (AgileUtil.isNotEmpty(fieldValue)) {
                String stringValue = fieldValue.toString();
                if (AgileUtil.isNumeric(fieldValue)) {
                    // 文件存放 服务类
                    IFileService fileService = SpringBeanUtils.getBean(IFileService.class);
                    if (AgileUtil.isNotEmpty(fileService)) {
                        File file = fileService.getById(stringValue);
                        // 文件地址
                        stringValue = file.getFileUrl();
                    }
                }
                /*
                 创建一个新的客户端锚点，并通过单元格引用和偏移设置锚点的左上角和右下角坐标。将类型设置为 ClientAnchor.AnchorType.MOVE_AND_RESIZE 。
                 参数： dx1 – 第一个单元格内的 x 坐标。
                 dy1 - 第一个单元格内的 y 坐标。
                 dx2 – 第二个单元格内的 x 坐标。
                 dy2 – 第二个单元格内的 y 坐标。
                 col1 – 第一个单元格的列（基于 0）。
                 row1 – 第一个单元格的行（基于 0）。
                 col2 – 第二个单元格的列（基于 0）。
                 row2 – 第二个单元格的行（基于 0）
                 */
                ClientAnchor clientAnchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
                // 创建画布
                Drawing<?> drawing = this.getDrawingPatriarch(cell.getSheet());
                // 读取文件为字节数据
                byte[] data = this.readFile(stringValue);
                // 图片插入类型
                int imageFileType = this.getImageFileType(data);
                // 创建图片
                drawing.createPicture(clientAnchor, cell.getSheet().getWorkbook().addPicture(data, imageFileType));
            }
        }
    }

    /**
     * 合计统计信息
     *
     * @param column          - 列
     * @param fieldValue      - 单元格值
     * @param excelAnnotation - 注解相关
     */
    private void addStatisticsData(final Integer column, final Object fieldValue, final Excel excelAnnotation) {
        if (excelAnnotation != null && excelAnnotation.isStatistics()) {
            BigDecimal temp = new BigDecimal(StringPool.ZERO);
            if (!this.statistics.containsKey(column)) {
                this.statistics.put(column, temp.toString());
            }
            if (AgileUtil.isNumeric(fieldValue)) {
                temp = new BigDecimal(fieldValue.toString());
            }
            this.statistics.put(column, new BigDecimal(this.statistics.get(column)).add(temp).toString());
        }
    }

    /**
     * 添加单元格
     *
     * @param fieldObject  - 注解列表
     * @param row          - 行对象
     * @param exportObject - 导出对象
     * @param column       - 列
     */
    private Cell addCell(final Object[] fieldObject, final Row row, final T exportObject, final int column) {
        // Excel 注解
        Excel excelAnnotation = (Excel) fieldObject[1];
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight(this.maxHeight);
            // 根据 Excel 中设置情况决定是否导出，有些情况需要保持为空，希望用户填写这一列
            if (excelAnnotation.isExport()) {
                // 创建 Cell
                cell = row.createCell(column);
                // 设置单元格样式
                int align = excelAnnotation.align().value();
                cell.setCellStyle(this.styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
                // 字段
                Field field = (Field) fieldObject[0];
                // 读取对象中的属性值
                Object fieldValue = this.getTargetValue(exportObject, field, excelAnnotation);
                // 日期格式
                String dateTimeFormat = excelAnnotation.dateTimeFormat();
                // 读取内容转换器表达式
                String readConverterExpression = excelAnnotation.readConverterExpression();
                // 分隔符，读取字符串组内容
                String separator = excelAnnotation.separator();
                // 字典的 code 值
                String dictCode = excelAnnotation.dictCode();
                if (AgileUtil.isNotEmpty(dateTimeFormat) && AgileUtil.isNotEmpty(fieldValue)) {
                    // 日期格式 数据
                    cell.setCellValue(new SimpleDateFormat(dateTimeFormat).format(fieldValue));
                } else if (AgileUtil.isNotEmpty(readConverterExpression) && AgileUtil.isNotEmpty(fieldValue)) {
                    // 解析导出值
                    cell.setCellValue(this.convertByExpression(fieldValue.toString(), readConverterExpression, separator));
                } else if (AgileUtil.isNotEmpty(dictCode) && AgileUtil.isNotEmpty(fieldValue)) {
                    // 解析字典值
                    cell.setCellValue(this.convertDictByExpression(fieldValue.toString(), dictCode, separator));
                } else if (fieldValue instanceof BigDecimal && -1 != excelAnnotation.scale()) {
                    // BigDecimal 精度 / BigDecimal 舍入规则
                    cell.setCellValue((((BigDecimal) fieldValue).setScale(excelAnnotation.scale(), excelAnnotation.roundingMode())).toString());
                } else if (!excelAnnotation.handler().equals(ExcelHandlerAdapter.class)) {
                    // 根据 自定义 Excel数据格式处理适配器 处理数据
                    cell.setCellValue(this.dataFormatHandlerAdapter(fieldValue, excelAnnotation));
                } else {
                    // 设置单元格内容
                    this.setCellValue(fieldValue, excelAnnotation, cell);
                }
                // 合计统计信息
                this.addStatisticsData(column, fieldValue, excelAnnotation);
            }
        } catch (Exception e) {
            log.error("导出Excel失败，添加单元格异常：{}", e.getMessage());
            ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertFail(e.getMessage());
        }
        return cell;
    }

    /**
     * 填充 Excel 数据
     *
     * @param sheetIndex - Sheet 序号
     * @param row        - 单元格行
     */
    private void fillExcelData(final int sheetIndex, Row row) {
        // 1、计算开始、结束
        int startNo = sheetIndex * SHEET_SIZE;
        int endNo = Math.min(startNo + SHEET_SIZE, list.size());
        for (int i = startNo; i < endNo; i++) {
            // 2、创建行
            row = this.sheet.createRow(i + 1 + rowNumber - startNo);
            // 3、得到导出对象
            T exportObject = list.get(i);
            int column = 0;
            for (Object[] fieldObject : this.fields) {
                // 4、添加单元格
                this.addCell(fieldObject, row, exportObject, column++);
            }
        }
    }

    /**
     * 创建统计行
     */
    private void addStatisticsRow() {
        if (this.statistics.size() > 0) {
            Row row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
            Set<Integer> keys = this.statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(this.styles.get("total"));
            cell.setCellValue("合计");
            for (Integer key : keys) {
                cell = row.createCell(key);
                cell.setCellStyle(this.styles.get("total"));
                cell.setCellValue(this.statistics.get(key));
            }
            this.statistics.clear();
        }
    }

    /**
     * 写入数据到 Sheet
     */
    private void writeSheet() {
        // 1、计算需要多少个 Sheet（Excel Sheet 最大行数，默认65536，超出此行数需要分多个 Sheet）
        int sheetQuantity = Math.max(1, (int) Math.ceil(list.size() * 1.0 / SHEET_SIZE));
        for (int sheetIndex = 0; sheetIndex < sheetQuantity; sheetIndex++) {
            // 2、创建工作表
            this.createSheet(sheetQuantity, sheetIndex);
            // 3、产生一行
            Row row = this.sheet.createRow(this.rowNumber);
            int column = 0;
            // 4、写入各个字段的列头名称
            for (Object[] os : this.fields) {
                Excel excelAnnotation = (Excel) os[1];
                // 4.1、创建单元格
                this.createCell(excelAnnotation, row, column++);
            }
            if (Excel.Type.EXPORT.equals(type)) {
                // 4.2、填充 Excel 数据
                this.fillExcelData(sheetIndex, row);
                // 4.3、创建统计行
                this.addStatisticsRow();
            }
        }
    }

    /**
     * 将 List 里面的数据导入到 Excel
     *
     * @param httpServletResponse - 返回数据
     */
    private void exportExcel(HttpServletResponse httpServletResponse) {
        try {
            // 写入数据到 Sheet
            this.writeSheet();
            // 写入数据到 Workbook
            workbook.write(httpServletResponse.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            // 安静地（没有例外）关闭可关闭资源。如果出现错误，它将被打印到IOUtils类记录器
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     * 将 List 里面的数据导入到 Excel
     *
     * @param httpServletResponse - 返回数据
     * @param list                - 导出数据集合
     * @param sheetName           - 工作表的名称
     */
    public void exportExcel(final HttpServletResponse httpServletResponse, final List<T> list, final String sheetName) {
        this.exportExcel(httpServletResponse, list, sheetName, StringPool.EMPTY);
    }

    /**
     * 将 List 里面的数据导入到 Excel
     *
     * @param httpServletResponse - 返回数据
     * @param list                - 导出数据集合
     * @param sheetName           - 工作表的名称
     * @param title               - Excel 标题
     */
    public void exportExcel(final HttpServletResponse httpServletResponse, final List<T> list, final String sheetName, final String title) {
        // 设置 HttpServletResponse
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setCharacterEncoding(StringPool.UTF_8);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + title);
        // 初始化 Excel
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        // 导出 Excel
        exportExcel(httpServletResponse);
    }

    //********************************************************** Excel 模版 导出 Start **********************************************************/

    /**
     * 导出 Excel 导入 模版
     *
     * @param httpServletResponse - 返回数据
     * @param sheetName           - 工作表的名称
     */
    public void exportExcelTemplate(final HttpServletResponse httpServletResponse, final String sheetName) {
        exportExcelTemplate(httpServletResponse, sheetName, StringPool.EMPTY);
    }

    /**
     * 导出 Excel 导入 模版
     *
     * @param httpServletResponse - 返回数据
     * @param sheetName           - 工作表的名称
     * @param title               - Excel 标题
     */
    public void exportExcelTemplate(final HttpServletResponse httpServletResponse, final String sheetName, final String title) {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + title);
        httpServletResponse.setCharacterEncoding(StringPool.UTF_8);
        this.init(null, sheetName, title, Excel.Type.IMPORT);
        exportExcel(httpServletResponse);
    }

    //********************************************************** Excel 文件 导入 Start **********************************************************/

    /**
     * 获取 Excel2003 图片集合
     *
     * @param sheet    - 当前 Sheet 对象
     * @param workbook - 工作簿对象
     * @return Map - key:图片单元格索引（1_1）String；value:图片流PictureData
     */
    private Map<String, PictureData> getSheetPictures03(final HSSFSheet sheet, final HSSFWorkbook workbook) {
        List<HSSFShape> hssfShapeList = sheet.getDrawingPatriarch().getChildren();
        // 索引 图片
        Map<String, PictureData> sheetIndexPictureMap = new HashMap<>(hssfShapeList.size());
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (!pictures.isEmpty()) {
            for (HSSFShape shape : hssfShapeList) {
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                if (shape instanceof HSSFPicture picture) {
                    int pictureIndex = picture.getPictureIndex() - 1;
                    HSSFPictureData pictureData = pictures.get(pictureIndex);
                    String pictureIndexString = anchor.getRow1() + StringPool.UNDERSCORE + String.valueOf(anchor.getCol1());
                    sheetIndexPictureMap.put(pictureIndexString, pictureData);
                }
            }
        }
        return sheetIndexPictureMap;
    }

    /**
     * 获取 Excel2007 图片集合
     *
     * @param sheet - 当前 Sheet 对象
     * @return Map - key:图片单元格索引（1_1）String；value:图片流 PictureData
     */
    private Map<String, PictureData> getSheetPictures07(final XSSFSheet sheet) {
        // 索引 图片
        Map<String, PictureData> sheetIndexPictureMap = new HashMap<>(16);
        for (POIXMLDocumentPart documentPart : sheet.getRelations()) {
            if (documentPart instanceof XSSFDrawing drawing) {
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    if (shape instanceof XSSFPicture picture) {
                        XSSFClientAnchor anchor = picture.getPreferredSize();
                        CTMarker ctMarker = anchor.getFrom();
                        String pictureIndex = ctMarker.getRow() + StringPool.UNDERSCORE + ctMarker.getCol();
                        sheetIndexPictureMap.put(pictureIndex, picture.getPictureData());
                    }
                }
            }
        }
        return sheetIndexPictureMap;
    }

    /**
     * 获取单元格值
     *
     * @param row    - 获取的行
     * @param column - 获取单元格列号
     * @return Object - 单元格值
     */
    private Object getCellValue(final Row row, final int column) {
        if (row == null) {
            return null;
        }
        Object cellValue = "";
        try {
            Cell cell = row.getCell(column);
            if (AgileUtil.isNotEmpty(cell)) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    cellValue = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // POI Excel 日期格式转换
                        cellValue = DateUtil.getJavaDate((Double) cellValue);
                    } else {
                        if ((Double) cellValue % 1 != 0) {
                            cellValue = new BigDecimal(cellValue.toString());
                        } else {
                            cellValue = new DecimalFormat(StringPool.ZERO).format(cellValue);
                        }
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    cellValue = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    cellValue = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    cellValue = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return cellValue;
        }
        return cellValue;
    }

    /**
     * 判断是否是空行
     *
     * @param row - 判断的行
     * @return Boolean - 否是空行
     */
    private Boolean isRowEmpty(final Row row) {
        if (row == null) {
            return Boolean.TRUE;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 将字段值设置为基本数据类型
     *
     * @param fieldValue - 字段值
     * @param field      - Field
     * @return Object - 基本数据类型
     */
    private Object makeFieldValueToBasicDataType(Object fieldValue, final Field field) {
        // 获取数据对象类型
        Class<?> fieldType = field.getType();
        // 日期格式
        String dateTimeFormat = field.getAnnotation(Excel.class).dateTimeFormat();
        boolean formatDateTime = AgileUtil.isNotEmpty(dateTimeFormat);
        // 万能字符串
        String stringValue = fieldValue.toString();
        // 判断字符串是否 数字
        boolean isNumeric = AgileUtil.isNumeric(fieldValue);
        if (String.class == fieldType) {
            if (formatDateTime) {
                stringValue = new SimpleDateFormat(dateTimeFormat).format(fieldValue);
            }
            return stringValue;
        } else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && isNumeric) {
            return Integer.parseInt(stringValue.trim());
        } else if ((Long.TYPE == fieldType || Long.class == fieldType) && isNumeric) {
            return Long.parseLong(stringValue.trim());
        } else if ((Double.TYPE == fieldType || Double.class == fieldType) && isNumeric) {
            return Double.parseDouble(stringValue.trim());
        } else if ((Float.TYPE == fieldType || Float.class == fieldType) && isNumeric) {
            return Float.parseFloat(stringValue.trim());
        } else if ((BigDecimal.class == fieldType) && isNumeric) {
            return BigDecimal.valueOf(Long.parseLong(stringValue.trim()));
        } else if (Date.class == fieldType) {
            if (fieldValue instanceof String) {
                // 字符串类型的日期
                dateTimeFormat = formatDateTime ? dateTimeFormat : CommonConstant.NORM_DATETIME_PATTERN;
                try {
                    return new SimpleDateFormat(dateTimeFormat).parse(stringValue.trim());
                } catch (ParseException e) {
                    log.error("格式化字符串类型日期异常：{}", e.getMessage());
                    ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertFail(e.getMessage());
                }
            } else if (fieldValue instanceof Double && isNumeric) {
                // Double 类型的日期
                return DateUtil.getJavaDate(Double.parseDouble(stringValue.trim()));
            }
        } else if (LocalDate.class == fieldType) {
            if (fieldValue instanceof String) {
                // 字符串类型的日期
                dateTimeFormat = formatDateTime ? dateTimeFormat : CommonConstant.NORM_DATE_PATTERN;
                return LocalDate.parse(stringValue.trim(), DateTimeFormatter.ofPattern(dateTimeFormat));
            } else if (fieldValue instanceof Date) {
                // Date 类型的日期
                return DateUtil.toLocalDateTime((Date) fieldValue).toLocalDate();
            } else if (fieldValue instanceof Double && isNumeric) {
                // Double 类型的日期
                return DateUtil.getLocalDateTime(Double.parseDouble(stringValue.trim())).toLocalDate();
            }
        } else if (LocalTime.class == fieldType) {
            if (fieldValue instanceof String) {
                // 字符串类型的日期
                dateTimeFormat = formatDateTime ? dateTimeFormat : CommonConstant.NORM_TIME_PATTERN;
                return LocalTime.parse(stringValue.trim(), DateTimeFormatter.ofPattern(dateTimeFormat));
            } else if (fieldValue instanceof Date) {
                // Date 类型的日期
                return DateUtil.toLocalDateTime((Date) fieldValue).toLocalTime();
            } else if (fieldValue instanceof Double && isNumeric) {
                // Double 类型的日期
                return DateUtil.getLocalDateTime(Double.parseDouble(stringValue.trim())).toLocalTime();
            }
        } else if (LocalDateTime.class == fieldType) {
            if (fieldValue instanceof String) {
                // 字符串类型的日期
                dateTimeFormat = formatDateTime ? dateTimeFormat : CommonConstant.NORM_DATETIME_PATTERN;
                return LocalDateTime.parse(stringValue.trim(), DateTimeFormatter.ofPattern(dateTimeFormat));
            } else if (fieldValue instanceof Date) {
                // Date 类型的日期
                return DateUtil.toLocalDateTime((Date) fieldValue);
            } else if (fieldValue instanceof Double && isNumeric) {
                // Double 类型的日期
                return DateUtil.getLocalDateTime(Double.parseDouble(stringValue.trim()));
            }
        } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
            return switch (stringValue.trim().toLowerCase()) {
                case "true", "yes", "ok", "1" -> Boolean.TRUE;
                default -> Boolean.FALSE;
            };
        }
        return fieldValue;
    }

    /**
     * 反向解析值 <br>
     * 例如：男=0,女=1,未知=2
     *
     * @param propertyValue       - 参数值
     * @param converterExpression - 翻译注解
     * @param separator           - 分隔符
     * @return String - 解析后值
     */
    private String reverseByExpression(final String propertyValue, final String converterExpression, final String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExpression.split(StringPool.COMMA);
        for (String item : convertSource) {
            String[] itemArray = item.split(StringPool.EQUALS);
            if (propertyValue.contains(separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return AgileUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 反向解析值字典项值
     *
     * @param itemName  - 字典项文本
     * @param dictCode  - 字典编码
     * @param separator - 分隔符
     * @return String - 字典项值
     */
    private String reverseDictByExpression(final String itemName, final String dictCode, final String separator) {
        StringBuilder propertyString = new StringBuilder();
        // 字典 - 值 服务类
        IDictItemService dictItemService = SpringBeanUtils.getBean(IDictItemService.class);
        if (AgileUtil.isNotEmpty(dictItemService)) {
            // 字典 - 值 查询
            List<DictItemVO> dictItemList = dictItemService.queryDictItemListByDictCode(dictCode);
            if (itemName.contains(separator) && AgileUtil.isNotEmpty(dictItemList)) {
                for (DictItemVO item : dictItemList) {
                    for (String label : itemName.split(separator)) {
                        if (label.equals(item.getName())) {
                            propertyString.append(item.getValue()).append(separator);
                            break;
                        }
                    }
                }
            } else {
                for (DictItemVO item : dictItemList) {
                    if (itemName.equals(item.getName())) {
                        return item.getValue();
                    }
                }
            }
        }
        return AgileUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 数据处理器
     *
     * @param value           - 数据值
     * @param excelAnnotation - Excel 注解
     * @return String - 格式化后的 数据值
     */
    private String dataFormatHandlerAdapter(Object value, final Excel excelAnnotation) {
        try {
            Object instance = excelAnnotation.handler().getDeclaredConstructor().newInstance();
            Method formatMethod = excelAnnotation.handler().getMethod("format", Object.class, String[].class);
            value = formatMethod.invoke(instance, value, excelAnnotation.args());
        } catch (Exception e) {
            log.error("不能格式化数据 " + excelAnnotation.handler() + "：{}", e.getMessage());
        }
        return value.toString();
    }

    /**
     * 处理图片数据，返回存放路径（文件ID）
     *
     * @param pictureData - 图片数据
     * @param description - 获取图片的 Key
     * @return String - 图片存放路径（文件ID）
     */
    private String processingPictureData(final PictureData pictureData, final String description) {
        if (pictureData != null) {
            byte[] data = pictureData.getData();
            String imageFileExtendName = StringPool.DOT + getImageFileExtendName(data);
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + description + imageFileExtendName;
            // 文件本地保存（uploadPath跟jar包同级目录，自动拼接 “./”）
            String filePath = StringPool.DOT + StringPool.SLASH + "Excel";
            String savePath = filePath + StringPool.SLASH + fileName;
            FileUtil.writeBytes(data, filePath, savePath);
            // 保存文件记录
            File file = new File()
                    .setFileType(imageFileExtendName)
                    .setFileSize((long) data.length)
                    .setDelFlag(CommonConstant.DEL_FLAG_0)
                    .setFileName(fileName)
                    .setDescription(description)
                    .setFileUrl(savePath);
            // 文件存放 服务类
            IFileService fileService = SpringBeanUtils.getBean(IFileService.class);
            if (AgileUtil.isNotEmpty(fileService)) {
                return fileService.save(file) ? file.getId().toString() : savePath;
            }
        }
        return StringPool.EMPTY;
    }

    /**
     * 将 Excel 第一个 Sheet 转换成 List<T>
     *
     * @param inputStream - Excel 输入流
     * @return List<T> - 转换后集合
     */
    public List<T> importExcel(InputStream inputStream) {
        return importExcel(inputStream, 0);
    }

    /**
     * 将 Excel 第一个 Sheet 转换成 List<T>
     *
     * @param inputStream - Excel 输入流
     * @param titleNum    - 标题占用行数
     * @return List<T> - 转换后集合
     */
    public List<T> importExcel(InputStream inputStream, int titleNum) {
        return importExcel(StringPool.EMPTY, inputStream, titleNum);
    }

    /**
     * 将 Excel 指定 Sheet 转换成 List<T>
     *
     * @param sheetName   - 表格索引名
     * @param inputStream - Excel 输入流
     * @param titleNum    - 标题占用行数
     * @return List<T> - 转换后集合
     */
    public List<T> importExcel(final String sheetName, final InputStream inputStream, final int titleNum) {
        this.type = Excel.Type.IMPORT;
        try {
            this.workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            log.error("从给定的 InputStream 中创建适当的 Workbook（HSSFWorkbook / XSSFWorkbook） 失败：{}", e.getMessage());
            ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertFail(e.getMessage());
        }
        List<T> list = new ArrayList<>();
        // 1、获取 Sheet ：如果指定 Sheet 名, 则取指定 Sheet 中的内容，否则默认指向第1个 Sheet
        Sheet sheet = AgileUtil.isNotEmpty(sheetName) ? workbook.getSheet(sheetName) : workbook.getSheetAt(0);
        // 2、判断 Sheet 对象是否为空，为空抛出异常
        ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertNotNull(sheet, "Sheet 文件 不存在");
        // 3、判断 Excel 版本，根据版本选择合适的 图片数据获取方式
        boolean isXSSFWorkbook = !(workbook instanceof HSSFWorkbook);
        Map<String, PictureData> pictures;
        if (isXSSFWorkbook) {
            // 3.1获取 Excel2007 图片集合
            pictures = this.getSheetPictures07((XSSFSheet) sheet);
        } else {
            // 3.2获取 Excel2003 图片集合
            pictures = this.getSheetPictures03((HSSFSheet) sheet, (HSSFWorkbook) workbook);
        }
        // 获取最后一个非空行的行下标，比如总行数为 n，则返回的为 n-1
        int rows = sheet.getLastRowNum();
        // 4、处理行数据
        if (rows > 0) {
            // 4.1获取 Excel 表头 所在行
            Row heard = sheet.getRow(titleNum);
            // 4.2定义一个 Map 用于存放 Excel 列的 Field 和 序号
            Map<String, Integer> cellMap = new HashMap<>(heard.getPhysicalNumberOfCells());
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                Cell cell = heard.getCell(i);
                String value = null;
                if (AgileUtil.isNotEmpty(cell)) {
                    value = this.getCellValue(heard, i).toString();
                }
                cellMap.put(value, i);
            }
            // 4.3有数据时才处理 得到类的所有 Field
            List<Object[]> fields = this.getFieldList();
            Map<Integer, Object[]> fieldsMap = new HashMap<>(fields.size());
            for (Object[] objects : fields) {
                Excel attr = (Excel) objects[1];
                Integer column = cellMap.get(attr.name());
                if (column != null) {
                    fieldsMap.put(column, objects);
                }
            }
            // 4.4处理行数据
            for (int i = titleNum + 1; i <= rows; i++) {
                // 4.4.1从标题行的下一行开始 获取数据处理
                Row row = sheet.getRow(i);
                // 判断当前行是否是空行
                if (this.isRowEmpty(row)) {
                    continue;
                }
                T entity = null;
                StringBuilder stringBuilder = new StringBuilder(StringPool.LEFT_BRACE);
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet()) {
                    // 4.4.2如果不存在实例则新建实例
                    try {
                        entity = (entity == null ? this.clazz.getDeclaredConstructor().newInstance() : entity);
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        log.error("创建由此Class对象表示的类的新实例失败：{}", e.getMessage());
                        ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertFail(e.getMessage());
                    }
                    // 4.4.3从 Map 中得到对应列的 Field
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 4.4.4取得对象类型，并根据对象类型设置值
                    Class<?> fieldType = field.getType();
                    ArgumentResponseEnum.EXCEL_IMPORT_ERR.assertNotNull(fieldType, "无效的字段的声明类型");
                    // 4.4.5处理 字段的 值 （Java 自带数据类型）
                    Object fieldValue = this.makeFieldValueToBasicDataType(this.getCellValue(row, entry.getKey()), field);
                    if (AgileUtil.isNotEmpty(attr.readConverterExpression())) {
                        // 4.4.6根据 内容转换器表达式 反向解析值
                        fieldValue = this.reverseByExpression(fieldValue.toString(), attr.readConverterExpression(), attr.separator());
                    } else if (AgileUtil.isNotEmpty(attr.dictCode())) {
                        // 4.4.6根据 字典的 code 值 反向解析值字典值
                        fieldValue = this.reverseDictByExpression(fieldValue.toString(), attr.dictCode(), attr.separator());
                    } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                        // 4.4.7自定义 数据格式处理适配器
                        fieldValue = this.dataFormatHandlerAdapter(fieldValue, attr);
                    } else if (Excel.ColumnType.IMAGE == attr.cellType() && AgileUtil.isNotEmpty(pictures)) {
                        // 4.4.8操作图片内容
                        // 获取图片的 Key
                        String key = row.getRowNum() + StringPool.UNDERSCORE + entry.getKey();
                        // 图片数据
                        PictureData pictureData = pictures.get(key);
                        // 处理图片数据，返回存放路径（文件ID）
                        fieldValue = this.processingPictureData(pictureData, key);
                    }
                    // 字段的 名称
                    String propertyName = field.getName();
                    if (AgileUtil.isNotEmpty(attr.targetAttr())) {
                        propertyName = field.getName() + StringPool.DOT + attr.targetAttr();
                    }
                    // 4.5 拼接 JSON 字符串 { 字段的名 : 字段的值 }
                    log.debug("字段：{} - 字段值： {}", propertyName, fieldValue);
                    stringBuilder.append(StringPool.QUOTE).append(propertyName).append(StringPool.QUOTE).append(StringPool.COLON);
                    stringBuilder.append(StringPool.QUOTE).append(fieldValue).append(StringPool.QUOTE).append(StringPool.COMMA);
                }
                stringBuilder.append(StringPool.RIGHT_BRACE);
                String entityString = stringBuilder.toString();
                if (entityString.endsWith(StringPool.COMMA + StringPool.RIGHT_BRACE)) {
                    entityString = entityString.replaceFirst(StringPool.COMMA + StringPool.RIGHT_BRACE, StringPool.RIGHT_BRACE);
                }
                // JSON 转 对象
                entity = JacksonUtils.parse(entityString, this.clazz);
                list.add(entity);
            }
        }
        return list;
    }

}
