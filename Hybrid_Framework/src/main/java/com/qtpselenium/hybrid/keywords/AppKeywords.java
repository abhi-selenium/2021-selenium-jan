package com.qtpselenium.hybrid.keywords;

import com.aventstack.extentreports.Status;

import java.io.IOException;

public class AppKeywords extends GenericKeywords{
    public void verifyLogin() throws IOException {
        test.log(Status.INFO, "Verifying Login");
        test.log(Status.INFO, "Expected Result: " + data.get("ExpectedResult"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getElementPresence(objectKey)) {
            if(data.get("ExpectedResult").equals("Success")){
                reportFailure("Login Failed");
            }
        } else {
            if(!data.get("ExpectedResult").equals("Success")){
                reportFailure("Login Failed");
            }
        }
        test.log(Status.INFO, "LOGIN VERIFIED");
    }

    public void addPortfolio() {
        test.log(Status.INFO, "Adding new portfolio");
        test.log(Status.INFO, "Portfolio Name: " + data.get("PortfolioName"));
    }
}
