package core.PageObjects.login.dashboard;

import core.Models.PageModel;
import core.PageObjects.login.BasePage;
import core.Wrappers.DriverWrapper;
import core.Wrappers.ElementWrapper;
import org.openqa.selenium.Alert;

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





    private void selectParentPage(String option){
        if(option != null) {
            ElementWrapper optionBox = new ElementWrapper(String.format("//select[@id='parent']/option[text()='%1$s']", option));
            System.out.println(optionBox.getElementXpath().toString());
            selectParentPageBox.click();
            optionBox.click();
        }
    }

    private void selectNumofCol(String option) {
        if(option != null) {
            ElementWrapper optionBox = new ElementWrapper(String.format("//select[@id='columnnumber']/option[text()='%1$s']", option));
            selectNumofCol.click();
            optionBox.click();
        }
    }

    private void selectDisplayAfter(String option) {
        if(option != null) {
            ElementWrapper optionBox = new ElementWrapper(String.format("//select[@id='afterpage']/option[text()='%1$s']", option));
            selectDisAfter.click();
            optionBox.click();
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

    public String selectMenu(String menu){
        String[] part = menu.split("/");
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
        ElementWrapper newPage = new ElementWrapper(String.format("//li[a[text()='%1$s']]", namePage));
        newPage.click();
        selectMenu("Delete");
        Alert alert = DriverWrapper.getInstance().switchToAlert();
        alert.accept();
    }
}
