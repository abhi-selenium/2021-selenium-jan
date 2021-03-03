package com.qtpselenium.hybrid.keywords;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class GenericKeywords {
    public String objectKey;
    public String dataKey;
    public Properties prop;
    public Properties envProp;
    public WebDriver driver = null;
    public Hashtable<String, String> data;
    public ExtentTest test;

    public void openBrowser() throws MalformedURLException {
        test.log(Status.INFO, "Opening " + data.get(dataKey) + " browser");
        if (prop.getProperty(Constants.GRID_VARIABLE).equals(Constants.GRID_YES)) {
            URL remoteAddress = new URL("http://192.168.0.108:4444/wd/hub");
            if (data.get(dataKey).equals("Chrome")) {
                ChromeOptions capabilities = new ChromeOptions();
                capabilities.addArguments("--start-maximized");
                driver = new RemoteWebDriver(remoteAddress, capabilities);
            } else if (data.get(dataKey).equals("Firefox")) {
                FirefoxOptions capabilities = new FirefoxOptions();
                driver = new RemoteWebDriver(remoteAddress, capabilities);
            }
        } else {
            if (data.get(dataKey).equals("Chrome")) {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            } else if (data.get(dataKey).equals("Firefox")) {
                driver = new FirefoxDriver();
            }
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public void navigate() {
        test.log(Status.INFO, "Navigating to - " + envProp.getProperty(objectKey));
        driver.navigate().to(envProp.getProperty(objectKey));
//        driver.get(envProp.getProperty(objectKey));
    }

    public void click() throws IOException {
        test.log(Status.INFO, "Clicking on - " + prop.getProperty(objectKey));
        getObject().click();
    }

    public void type() throws IOException {
        test.log(Status.INFO, "Typing '" + data.get(dataKey) + "' inside - " + prop.getProperty(objectKey));
        getObject().sendKeys(data.get(dataKey));
    }

    /********************** Utilities **********************/

    public WebElement getObject() throws IOException {
        WebElement object = null;
        try {
            if (objectKey.endsWith("_xpath")) {
                object = driver.findElement(By.xpath(prop.getProperty(objectKey)));
            } else if (objectKey.endsWith("_css")) {
                object = driver.findElement(By.cssSelector(prop.getProperty(objectKey)));
            } else if (objectKey.endsWith("_id")) {
                object = driver.findElement(By.id(prop.getProperty(objectKey)));
            } else if (objectKey.endsWith("_name")) {
                object = driver.findElement(By.name(prop.getProperty(objectKey)));
            }
        } catch (Exception exception) {
            reportFailure(exception.getMessage());
        }
        return object;
    }

    public boolean getElementPresence(String locatorKey) {
        List<WebElement> objects = null;
        if (objectKey.endsWith("_xpath")) {
            objects = driver.findElements(By.xpath(prop.getProperty(objectKey)));
        } else if (objectKey.endsWith("_css")) {
            objects = driver.findElements(By.cssSelector(prop.getProperty(objectKey)));
        } else if (objectKey.endsWith("_id")) {
            objects = driver.findElements(By.id(prop.getProperty(objectKey)));
        } else if (objectKey.endsWith("_name")) {
            objects = driver.findElements(By.name(prop.getProperty(objectKey)));
        }
        return objects.size() > 0;
    }

    public void reportFailure(String message) throws IOException {
        test.log(Status.FAIL, message);
        attachScreenshot(Status.FAIL);
        Assert.fail(message);
    }

    public void attachScreenshot(Status status) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotFileName = ExtentManager.screenshotsFolderName + Constants.SIMPLE_DATE_FORMAT.format(new Date()) + ".png";
        FileUtils.copyFile(screenshot, new File(screenshotFileName));
        test.log(status, "Screenshot Here" + test.addScreenCaptureFromPath(screenshotFileName, "Screenshot at the time of Execution"));
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public void setEnvProp(Properties envProp) {
        this.envProp = envProp;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public void setData(Hashtable<String, String> data) {
        this.data = data;
    }

    public void setTest(ExtentTest test) {
        this.test = test;
    }
}
