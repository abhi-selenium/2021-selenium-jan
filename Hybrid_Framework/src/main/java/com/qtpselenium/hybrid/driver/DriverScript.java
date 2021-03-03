package com.qtpselenium.hybrid.driver;

import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.keywords.AppKeywords;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.XLS_Reader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

public class DriverScript {
    public AppKeywords app = null;
    public void executeKeywords(String testName, ExtentTest test, Hashtable<String, String> data, XLS_Reader xls, Properties prop, Properties envProp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        app = new AppKeywords();
        app.setProp(prop);
        app.setEnvProp(envProp);
        app.setTest(test);
        for (int rowNum = 2; rowNum <= xls.getRowCount(Constants.KEYWORDS_SHEET); rowNum++) {
            String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rowNum);
            if (tcid.equals(testName)) {
                String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rowNum);
                String objectKey = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rowNum);
                String dataKey = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rowNum);
                app.setObjectKey(objectKey);
                app.setDataKey(dataKey);
                app.setData(data);
                Method method = app.getClass().getMethod(keyword);
                method.invoke(app);
            }
        }
    }
}
