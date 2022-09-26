package core.Utils;

public class Constant {
    public static final int WAIT_TIMEOUT = 20;
    public static final int WAIT_MEDIUM_TIMEOUT = 10;
    public static final int LONG_WAIT_TIMEOUT = 40;
    public static final String ALLURE_RESULT_PATH = System.getProperty("user.dir") + "/allure-results";
    public static final String HUB_URL = "http://localhost:4444/wd/hub";

    public static final String AUT_URL = "http://localhost/TADashboard/login.jsp";

    public static final String INVALID_LOGIN_MESSAGE = "Username or password is invalid";
    public static final String BLANK_USERNAME_ERROR = "Please enter username!";

    public static final String[] PRESET_DATA_PROFILE = {"Action Implementation By Status",
                                                        "Functional Tests",
                                                        "Test Case Execution",
                                                        "Test Case Execution Failure Trend",
                                                        "Test Case Execution History",
                                                        "Test Case Execution Results",
                                                        "Test Case Execution Trend",
                                                        "Test Module Execution",
                                                        "Test Module Execution Failure Trend",
                                                        "Test Module Execution Failure Trend by Build",
                                                        "Test Module Execution History",
                                                        "Test Module Execution Results",
                                                        "Test Module Execution Results Report",
                                                        "Test Module Execution Trend",
                                                        "Test Module Execution Trend by Build",
                                                        "Test Module Implementation By Priority",
                                                        "Test Module Implementation By Status",
                                                        "Test Module Status per Assigned Users",
                                                        "Test Objective Execution"};
    }
