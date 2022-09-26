package core.PageObjects.login;

import core.Wrappers.DriverWrapper;
import core.Wrappers.ElementWrapper;
import core.Enums.HeadMenu;
import core.Enums.MenuItem;
import org.openqa.selenium.Alert;

public class BasePage {

    // Locators
    private String headMenuLocator(HeadMenu menu) {
        return String.format("//ul[@class='head-menu']/li/a[text()='%s']", menu.toString());
    }

    private String menuItemLocator(MenuItem item) {
        return String.format("//li/a[text()='%s']", item);
    }

    // WebElements
    public ElementWrapper headMenu(HeadMenu menu) {
        return new ElementWrapper(headMenuLocator(menu));
    }

    public ElementWrapper menuItem(MenuItem item) {
        return new ElementWrapper(menuItemLocator(item));
    }

    // Methods
    public void logout() {
        headMenu(HeadMenu.ADMINISTRATOR).click();
        menuItem(MenuItem.LOGOUT).click();
    }

    public String getDashboardErrorMessage() {
        Alert alert = DriverWrapper.getInstance().switchToAlert();
        return alert.getText();
    }

    public void closeAlert() {
        Alert alert = DriverWrapper.getInstance().switchToAlert();
        alert.dismiss();
    }
}
