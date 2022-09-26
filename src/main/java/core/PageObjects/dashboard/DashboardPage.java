package core.PageObjects.dashboard;

import core.Models.PageModel;
import core.PageObjects.BasePage;
import core.Wrappers.DriverWrapper;
import core.Wrappers.ElementWrapper;
import core.Enums.GlobalSettingMenu;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/**
 * (TODO) Display name: Execution Dashboard Page
 */
public class DashboardPage extends BasePage {

    ElementWrapper currentTab = new ElementWrapper("//div[@id='main-menu']//ul/li[@class='active']");

    ElementWrapper globalSetting = new ElementWrapper("//li[@class='mn-setting']");
    ElementWrapper namePageDialogBox = new ElementWrapper("//input[@id='name']");
    ElementWrapper okDialogBoxButton = new ElementWrapper("//input[@id='OK']");
    ElementWrapper selectParentPageBox = new ElementWrapper("//select[@id='parent']");
    ElementWrapper selectNumofCol = new ElementWrapper("//select[@id='columnnumber']");
    ElementWrapper selectDisAfter = new ElementWrapper("//select[@id='afterpage']");
    ElementWrapper checkPublicBox = new ElementWrapper("//input[@id='ispublic']");
    ElementWrapper dashboardHeader = new ElementWrapper("//div[@id='header']");




    private void selectParentPage(String option){
        if(option != null) {
            Select parentPage = new Select(DriverWrapper.getInstance().getDriver().findElement(By.id("parent")));
            parentPage.selectByVisibleText(option);
        }
    }

    private void selectNumofCol(String option) {
        if(option != null) {
            Select numOfCol = new Select(DriverWrapper.getInstance().getDriver().findElement(By.id("columnnumber")));
            numOfCol.selectByVisibleText(option);
        }
    }

    private void selectDisplayAfter(String option) {
        if(option != null) {
            Select selectDisplayAfter = new Select(DriverWrapper.getInstance().getDriver().findElement(By.id("columnnumber")));
            selectDisplayAfter.selectByVisibleText(option);
        }
    }

    private void checkPublic(boolean isCheck) {
        if(isCheck){
            checkPublicBox.click();
        }
    }

    public boolean isDisplay() {
        return currentTab.getText().equals("Execution Dashboard");
    }

    public String selectMenu(GlobalSettingMenu menu){
        String[] part = menu.toString().split("/");
        String xpath = globalSetting.getElementXpath().toString();
        globalSetting.click();
        for (int i = 0; i < part.length; ++i) {
            xpath = xpath + String.format("//li[a[text()='%1$s']]", part[i]);
            ElementWrapper tag = new ElementWrapper(xpath);
            tag.click();
        }
        return xpath;
    }

    public  void fillDialogBox(PageModel page){
        namePageDialogBox.enter(page.getPageName());
        selectParentPage(page.getParentPage());
        selectNumofCol(page.getNumOfCol());
        selectDisplayAfter(page.getDisAfter());
        checkPublic(page.getPublic());
        okDialogBoxButton.click();
    }

    public boolean isSelectedMenu(){
        return globalSetting.isClickable(5);
    }

    public boolean isNewPageCreated(String namePage){
        ElementWrapper newPage = new ElementWrapper(String.format("//li[a[text()='%1$s']]", namePage));
        return newPage.isClickable(5);
    }

    public void deletedPage(String namePage){
        String[] namePageSplit = namePage.split("/");
        ElementWrapper parentPage = new ElementWrapper(String.format("//li[a[text()='%1$s']]", namePageSplit[0]));
        String temp = parentPage.getElementXpath().toString();

        if(namePageSplit.length > 1)parentPage.moveTo();
        else parentPage.click();

        for (int i = 1; i < namePageSplit.length; i++){
            ElementWrapper page = new ElementWrapper(temp+ String.format("//li[a[text()='%1$s']]", namePageSplit[i]));
            temp = page.getElementXpath().toString();
            page.click();
        }
        selectMenu(GlobalSettingMenu.DELETE);
        Alert alert = DriverWrapper.getInstance().switchToAlert();
        alert.accept();
        ElementWrapper page = new ElementWrapper(temp);
        Assert.assertTrue(page.waitForNotPresence(1000));
    }

    public  boolean isDashboardAppear(){
        return dashboardHeader.isDisplayed();
    }
}
