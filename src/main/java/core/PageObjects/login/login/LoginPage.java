package core.PageObjects.login.login;

import core.Models.AccountModel;
import core.PageObjects.login.BasePage;
import core.Wrappers.ElementWrapper;

public class LoginPage extends BasePage {

    private ElementWrapper listRepository = new ElementWrapper("//div[@class='form']//select");
    private ElementWrapper txtUsername = new ElementWrapper("//div[@class='form']//input[@id='username']");
    private ElementWrapper txtPassword = new ElementWrapper("//div[@class='form']//input[@id='password']");
    private ElementWrapper btnLogin = new ElementWrapper("//div[@class='form']//div[@class='btn-login']");


    public void enterUsername(String username) {
        txtUsername.enter(username);
    }

    public void enterPassword(String password) {
        txtPassword.enter(password);
    }

    public void enterAccount(AccountModel account) {
        enterAccount(account.getUsername(), account.getPassword());
    }

    public void enterAccount(String username, String password) {
        enterUsername(username);
        enterPassword(password);
    }

    public void clickLoginButton() {
        btnLogin.click();
    }

    public void login(AccountModel account) {
        login(account.getUsername(), account.getPassword());
    }

    public void login(String username, String password) {
        enterAccount(username, password);
        clickLoginButton();
    }

}
