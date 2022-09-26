package testcase;

import core.Factory.WebDriverFactory;
import core.Listener.TestAllureListener;
import core.Utils.Constant;
import core.Wrappers.DriverWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import testcase.da_main_parent.MainPage;

@Listeners({TestAllureListener.class})
public class TestBase {
    private static Logger logger = LogManager.getLogger(TestBase.class);

    @Parameters({"browser", "remote"})
    @BeforeMethod(alwaysRun = true)
    public void LaunchApplication(String browser, boolean remote) {
        DriverWrapper.getInstance().setDriver(WebDriverFactory.getWebDriver(browser));
        logger.info("Step 1: Navigate to URL");
        DriverWrapper.getInstance().get(Constant.AUT_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverWrapper.getInstance().closeBrowser();
    }
}
