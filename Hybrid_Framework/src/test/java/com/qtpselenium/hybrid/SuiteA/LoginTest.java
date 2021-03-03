package com.qtpselenium.hybrid.SuiteA;

import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.BaseTest;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.DataUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class LoginTest extends BaseTest {
    @Test(dataProvider = "getData")
    public void loginTest(Hashtable<String, String> data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if ((DataUtil.isTestSkip(testName, xls)) || (!data.get(Constants.RUNMODE_COL).equals(Constants.RUNMODE_YES))) {
            test.log(Status.SKIP, "RunMode is set to N");
            throw new SkipException("RunMode is set to " + "N");
        }
        test.log(Status.INFO, "Running " + testName);
        ds.executeKeywords(testName, test, data, xls, prop, envProp);
    }
}
