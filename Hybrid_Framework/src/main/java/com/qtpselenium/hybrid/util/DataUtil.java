package com.qtpselenium.hybrid.util;

import java.util.Hashtable;

public class DataUtil {
    public static boolean isTestSkip(String testName, XLS_Reader xls) {
        String runMode;
        for(int rowNum = 2; rowNum <= xls.getRowCount(Constants.TESTCASES_SHEET); rowNum++) {
            if (testName.equals(xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rowNum))) {
                if (xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rowNum).equals(Constants.RUNMODE_YES)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Object[][] getTestData(String testName, XLS_Reader xls) {
        int testStartRowNumber = 1;
        while(!(testName.equals(xls.getCellData(Constants.DATA_SHEET,0,testStartRowNumber)))) {
            testStartRowNumber++;
        }

        int colStartRowNum = testStartRowNumber + 1;
        int colCount = 0;
        while (!(xls.getCellData(Constants.DATA_SHEET, colCount, colStartRowNum).equals(""))) {
            colCount++;
        }

        int dataStartRowNum = colStartRowNum + 1;
        int rowCount = 0;
        while (!(xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum + rowCount).equals(""))) {
            rowCount ++;
        }

        Object[][] data = new Object[rowCount][1];

        for (int rowNum = 0; rowNum < rowCount; rowNum++) {
            Hashtable<String, String> dataTable = new Hashtable<>();
            for (int colNum = 0; colNum < colCount; colNum++) {
                String key = xls.getCellData(Constants.DATA_SHEET, colNum, colStartRowNum);
                String value = xls.getCellData(Constants.DATA_SHEET, colNum, rowNum + dataStartRowNum);
                dataTable.put(key,value);
            }
            data[rowNum][0] = dataTable;
        }
        return data;
    }
}
