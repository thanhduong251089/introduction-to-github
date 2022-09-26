package testcase.da_login;

import core.Enums.Accounts;
import core.Utils.Constant;
import core.Models.AccountModel;
import core.PageObjects.dashboard.DashboardPage;
import core.PageObjects.login.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcase.TestBase;
import testcase.da_main_parent.MainPage;

public class Login extends TestBase {

    private static Logger logger = LogManager.getLogger(Login.class);

    AccountModel account;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    String errorMsg;

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);

        logger.info("Step 2: Enter valid username and password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

         logger.info("Step 4 - VP 1: Verify that Dashboard Main page appears");
        Assert.assertTrue(dashboardPage.isDashboardAppear(), "Expect Dashboard Main page appears");

        logger.info("Post-Condition");
        dashboardPage.logout();
    }

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void TC002() {
        LoginPage loginPage = new LoginPage();

        account = new AccountModel(Accounts.INVALID_USERNAME, Accounts.INVALID_PASSWORD);
        errorMsg = "Username or password is invalid";

        logger.info("Step 2: Enter invalid username and password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

        logger.info("Step 4 - VP 1: Verify that Dashboard Error message \"Username or password is invalid\" appears");
        Assert.assertEquals(loginPage.getDashboardErrorMessage(), Constant.INVALID_LOGIN_MESSAGE, "Expect Dashboard Error message ''Username or password is invalid' appears");

        logger.info("Post-Condition");
        loginPage.closeAlert();
    }

    @Test(description = "Verify that user fails to log in specific repository successfully via Dashboard login page with correct username and incorrect password")
    public void TC003() {
        LoginPage loginPage = new LoginPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.INVALID_PASSWORD);
        errorMsg = "Username or password is invalid";

        logger.info("Step 2: Enter valid username and invalid password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

        logger.info("Step 4 - VP 1: Navigate to Dashboard login page");
        Assert.assertEquals(loginPage.getDashboardErrorMessage(), Constant.INVALID_LOGIN_MESSAGE, "Expect Dashboard Error message ''Username or password is invalid' appears");

        logger.info("Post-Condition");
        loginPage.closeAlert();
    }

    @Test(description = "Verify that user is able to log in different repositories successfully after logging out current repository")
    public  void TC0010() {
        LoginPage loginPage = new LoginPage();

        account = new AccountModel(Accounts.BLANK_USERNAME, Accounts.VALID_PASSWORD);
        errorMsg = "Please enter username!";

        logger.info("Step 2: Enter valid username and invalid password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

        logger.info("Step 4 - VP 1: Navigate to Dashboard login page");
        Assert.assertEquals(loginPage.getDashboardErrorMessage(), Constant.BLANK_USERNAME_ERROR, "Expect Dashboard Error message 'Please enter username!' appears");

        logger.info("Post-Condition");
        loginPage.closeAlert();
    }

}
