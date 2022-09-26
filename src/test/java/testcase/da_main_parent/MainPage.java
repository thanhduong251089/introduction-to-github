package testcase.da_main_parent;

import core.Enums.Accounts;
import core.Models.AccountModel;
import core.Models.PageModel;
import core.PageObjects.dashboard.DashboardPage;
import core.PageObjects.login.LoginPage;
import core.Enums.GlobalSettingMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcase.TestBase;


public class MainPage extends TestBase {
    private static Logger logger = LogManager.getLogger(MainPage.class);

    AccountModel account;
    PageModel page;
    PageModel childPage;

    @Test(description = "Verify that user is unable open more than 1 \"New Page\" dialog")
    public void TC011() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);


        logger.info("Step 2: Login with valid account");
        loginPage.enterAccount(account);
        loginPage.clickLoginButton();

        logger.info("Step 3: Add page");
        dashboardPage.selectMenu(GlobalSettingMenu.ADD_PAGE);

        logger.info("VP: Verify user can select Menu");
        Assert.assertTrue(dashboardPage.isSelectedMenu());

    }

    @Test(description = "Verify that user is able to add additional pages besides \"Overview\" page successfully")
    public void TC012() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);
        page = new PageModel("Test", null, null, null, false);

        logger.info("Login with valid account");
        loginPage.login(account);

        logger.info("Add page");
        dashboardPage.selectMenu(GlobalSettingMenu.ADD_PAGE);

        logger.info("Step 4: Enter Page Name field");
        dashboardPage.fillDialogBox(page);

        logger.info("VP: Verify new page is created");
        Assert.assertTrue(dashboardPage.isNewPageCreated(page.getPageName()));

        logger.info("post - condition");
        dashboardPage.deletedPage(page.getPageName());
    }

    @Test(description = "Verify that \"Public\" pages can be visible and accessed by all users of working repository")
    public void TC014() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);
        page = new PageModel("Test", null, null, null, true);

        logger.info("Step 2: Login with valid account");
        loginPage.login(account);

        logger.info("Step 3: Go to Global Setting -> Add page");
        dashboardPage.selectMenu(GlobalSettingMenu.ADD_PAGE);

        logger.info("Step 4: Enter Page Name field");
        dashboardPage.fillDialogBox(page);

        logger.info("VP: Verify new page is created");
        Assert.assertTrue(dashboardPage.isNewPageCreated(page.getPageName()));

        logger.info("Step 5: Logout");
        dashboardPage.logout();

        logger.info("Step 6: Log in");
        loginPage.login(account);

        logger.info("VP: Verify new page is created");
        Assert.assertTrue(dashboardPage.isNewPageCreated(page.getPageName()));

        logger.info("post - condition");
        dashboardPage.deletedPage(page.getPageName());

    }

    @Test(description = "Verify that \"Public\" pages can be visible and accessed by all users of working repository")
    public void TC015() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);
        page = new PageModel("Test", null, null, null, true);
        childPage = new PageModel("TestChild", "Test", null, null, false);

        logger.info("Step 2: Login with valid account");
        loginPage.login(account);

        logger.info("Step 3: Go to Global Setting -> Add page");
        dashboardPage.selectMenu(GlobalSettingMenu.ADD_PAGE);

        logger.info("Step 4: Enter Page Name field");
        dashboardPage.fillDialogBox(page);

        logger.info("post - condition");
        Assert.assertTrue(dashboardPage.isNewPageCreated(page.getPageName()));

        logger.info("Step 5: Add child page");
        dashboardPage.selectMenu(GlobalSettingMenu.ADD_PAGE);
        dashboardPage.fillDialogBox(childPage);

        logger.info("Step 6: log out");
        dashboardPage.logout();

        logger.info("Step 7: log in");
        loginPage.login(account);

        logger.info("VP: Verify new page is created");
        Assert.assertTrue(dashboardPage.isNewPageCreated(page.getPageName()));

        logger.info("post - condition");
        dashboardPage.deletedPage(page.getPageName()+"/"+childPage.getPageName());
        dashboardPage.deletedPage(page.getPageName());

    }
}
