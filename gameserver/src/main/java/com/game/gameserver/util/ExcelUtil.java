package com.game.gameserver.util;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel表格工具
 * @author xuewenkang
 * @date 2020/5/22 16:13
 */
public class ExcelUtil {
    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 将excel
     * @param clazz 类型
     * @return List<T>
     */
    public static <T> List<T>  parseExcel(String path,Class<T> clazz){
        List<T> list = new ArrayList<>();
        File file = new File(path);
        if(!file.exists()){
            logger.warn("load {} failed",path);
            return list;
        }

        // 获取函数
        Method[] methods = clazz.getDeclaredMethods();
        Map<String,Method> methodMap = new HashMap<>(16);
        for(Method method:methods){
            if(method.getName().contains("set")){
                String name = method.getName().substring(method.getName().lastIndexOf("set")).toUpperCase();
                methodMap.put(name,method);
            }
        }

        // 读取内容
        try {
            Workbook workbook = WorkbookFactory.create(file);
            int sheetCount = workbook.getNumberOfSheets();
            if(sheetCount == 0){
                return null;
            }
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum()+1;
            Row titleRow = sheet.getRow(1);
            for(int i=2;i<rows;i++){
                Row row = sheet.getRow(i);
                T instance = clazz.newInstance();
                for(int j=0;j<titleRow.getPhysicalNumberOfCells();j++){
                    String key = ("set"+titleRow.getCell(j).getStringCellValue()).toUpperCase();
                    Method method = methodMap.get(key);
                    if(method==null){
                        continue;
                    }
                    Object value = getValueFromCell(row.getCell(j),method.getParameterTypes()[0]);
                    method.invoke(instance,value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Object getValueFromCell(Cell cell, Class type){
        Object value = null;
        String typeName = type.getSimpleName().toUpperCase();
        if(cell==null){
            return null;
        }
        switch (cell.getCellType()){
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    break;
                }else{
                    Double cellValue = cell.getNumericCellValue();
                    if("INTEGER".equals(typeName)||"INT".equals(typeName)){
                        value = cellValue.intValue();
                    }
                    if("DOUBLE".equals(typeName)){
                        value = cellValue;
                    }
                    if("LONG".equals(typeName)){
                        value = cellValue.longValue();
                    }
                    if("FLOAT".equals(typeName)){
                        value = cellValue.floatValue();
                    }
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case FORMULA:
            case ERROR:
            case BLANK:
            case _NONE:
                break;
            default:{}
        }
        return value;
    }

}
