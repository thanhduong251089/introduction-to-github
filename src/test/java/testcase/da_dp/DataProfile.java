package testcase.da_dp;

import core.Enums.Accounts;
import core.Models.AccountModel;
import core.PageObjects.dashboard.DashboardPage;
import core.PageObjects.dashboard.administer.data_profiles.DataProfilesPage;
import core.PageObjects.login.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import testcase.TestBase;
import testcase.da_login.Login;

public class DataProfile extends TestBase {

    private static Logger logger = LogManager.getLogger(Login.class);
    AccountModel account;
    LoginPage loginPage;
    DataProfilesPage dataProfilesPage;
    DashboardPage dashboardPage;
    String errorMsg;

    @Test(description = "Verify that all Pre-set Data Profiles are populated correctly")
    public void TC065() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        DataProfilesPage dataProfilePage = new DataProfilesPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);

        logger.info("Step 2: Enter valid username and invalid password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

        logger.info("Step 4: Go to data profile page");
        dashboardPage.goToDataProfile();

        logger.info("VP: Verify preset data is correctly");
        dataProfilePage.isPreSetDataCorrectly();

        logger.info("post - condition");
        dashboardPage.logout();
    }

    @Test(description = "Verify preset data is sorted alphabetically")
    public void TC067() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        DataProfilesPage dataProfilePage = new DataProfilesPage();

        account = new AccountModel(Accounts.VALID_USERNAME, Accounts.VALID_PASSWORD);

        logger.info("Step 2: Enter valid username and password");
        loginPage.enterAccount(account);

        logger.info("Step 3: Click on \"Login\" button");
        loginPage.clickLoginButton();

        logger.info("Step 4: Go to data profile page");
        dashboardPage.goToDataProfile();

        logger.info("VP: Verify preset data is sorted alphabetically");
        dataProfilePage.isPresetDataSortAlphabetically();

        logger.info("post - condition");
        dashboardPage.logout();
    }
}
