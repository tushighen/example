package com.golomt.example.view;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {

    private static Map<String, String> STATUS_MAP = new HashMap<>();

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {


        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xls\"");

//        @SuppressWarnings("unchecked")
//        List<Task> tasks = (List<Task>) (model.get("tasks") != null ? model.get("tasks") : model.get("employees"));


        // create excel xls sheet
        Sheet sheet = workbook.createSheet("User Detail");
        sheet.setDefaultColumnWidth(20);

        // header cell style
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Aria");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        Row header = sheet.createRow(0);

//        if (model.get("tasks") != null) {
//            this.doCreateHeaderCell(TaskHeader.class, header, style);
//            int rowCount = 1;
//            for (Task task : tasks) {
//                Row row = sheet.createRow(rowCount++);
//                row.createCell(0).setCellValue(task.getTaskName());
//                row.createCell(1).setCellValue(task.getType());
//            }
//        }
    }

    private void doCreateHeaderCell(Class c, Row header, CellStyle style) {
        int i = 0;
        for (Field field : c.getDeclaredFields()) {
            try {
                if (field.getType().getName().equals("java.lang.String")) {
                    String instance = new String();
                    Object o = field.get(instance);
                    String s = ((String) o);
                    header.createCell(i).setCellValue(s);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            header.getCell(i).setCellStyle(style);
            i++;
        }
    }

    static Map<String, String> createMap() {
        if (STATUS_MAP.size() == 0) {
            STATUS_MAP.put("A", "Active");
            STATUS_MAP.put("P", "Pending");
            STATUS_MAP.put("S", "Шийдвэрлэсэн");
            STATUS_MAP.put("T", "Шилжүүлсэн");
        }
        return STATUS_MAP;
    }

}
