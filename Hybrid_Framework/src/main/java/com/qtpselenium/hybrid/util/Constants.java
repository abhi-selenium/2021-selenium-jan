package com.qtpselenium.hybrid.util;

import java.text.SimpleDateFormat;

public class Constants {
    // Sheet Names
    public static final String DATA_SHEET = "Data";
    public static final String KEYWORDS_SHEET = "Keywords";
    public static final String TESTCASES_SHEET = "TestCases";

    // Column Names
    public static final String TCID_COL = "TCID";
    public static final String KEYWORD_COL = "Keyword";
    public static final String OBJECT_COL = "Object";
    public static final String DATA_COL = "Data";
    public static final String RUNMODE_COL = "RunMode";

    public static final String RUNMODE_YES = "Y";

    // Paths
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String RESOURCES_FOLDER = PROJECT_PATH + "/src/test/resources/";
    public static final String REPORTS_FOLDER = System.getProperty("user.dir") + "/ExtentReports/";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("y_MM_dd_z_HH_mm_ss");
    public static final String GRID_VARIABLE = "grid";
    public static final String GRID_YES = "Y";
}
