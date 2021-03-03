package com.qtpselenium.hybrid.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    public static String reportsFolder;
    public static String screenshotsFolderName;

    public static ExtentReports extent;


    public static ExtentReports getInstance(String reportsRootPath) {
        if (extent == null) {
//            Current Date and
            String reportsFolderName = reportsRootPath + Constants.SIMPLE_DATE_FORMAT.format(new Date()) + "/";
            screenshotsFolderName = reportsFolderName + "screenshots/";
            new File(screenshotsFolderName).mkdirs();
            createInstance(reportsFolderName + "index.html");
        }
        return extent;
    }

    private static void createInstance(String htmlReportFilePath) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlReportFilePath);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setDocumentTitle("Test Automation Report");
        htmlReporter.config().setReportName("Execution Reports");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
}
