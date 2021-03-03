package com.qtpselenium.hybrid;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.DataUtil;
import com.qtpselenium.hybrid.util.ExtentManager;
import com.qtpselenium.hybrid.util.XLS_Reader;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class BaseTest {
    public XLS_Reader xls;
    public DriverScript ds;
    public String testName;
    public String suiteName;
    public Properties prop; // project.properties
    public Properties envProp; // based on env, prod.properties/uat.properties
    public ExtentReports extentReport;
    public ExtentTest test;

    @BeforeTest
    public void beforeTest() throws IOException {
        testName = this.getClass().getSimpleName();
        suiteName = this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 2];
        xls = new XLS_Reader(Constants.RESOURCES_FOLDER + suiteName + ".xlsx");
        ds = new DriverScript();
        prop = new Properties();
        envProp = new Properties();
        prop.load(new FileInputStream(Constants.RESOURCES_FOLDER + "project.properties"));
        envProp.load(new FileInputStream(Constants.RESOURCES_FOLDER + prop.getProperty("env") + ".properties"));
    }

    @BeforeMethod
    public void beforeMethod() {
        extentReport = ExtentManager.getInstance(Constants.REPORTS_FOLDER);
        test = extentReport.createTest(testName);
    }

    @AfterTest
    public void afterTest() {
        if(extentReport != null) {
            extentReport.flush();
        }
    }

    @AfterMethod
    public void afterMethod() {
        if(ds != null) {
            if (ds.app != null) {
                if (ds.app.driver != null) {
                    ds.app.driver.quit();
                }
            }
        }
    }

    @DataProvider
    public Object[][] getData() {
        return DataUtil.getTestData(testName, xls);
    }
}
