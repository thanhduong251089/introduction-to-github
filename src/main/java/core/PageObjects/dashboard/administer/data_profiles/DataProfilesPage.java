package core.PageObjects.dashboard.administer.data_profiles;

import core.Utils.Constant;
import core.PageObjects.dashboard.DashboardPage;
import core.Wrappers.ElementWrapper;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;

public class DataProfilesPage extends DashboardPage {

    ElementWrapper addNewSetting = new ElementWrapper("//a[text()='Add new']");
    ElementWrapper deletedSetting = new ElementWrapper("//a[text()='Delete']");

    ElementWrapper dataProfile = new ElementWrapper("//table[@class='GridView']//tr//td[count(//table//tr//th[text()='Data Profile']/preceding-sibling::th)+1]");

    public List<String> getAllDataProfile(){
        return dataProfile.getAllTexts();
    }

    public void isPreSetDataCorrectly(){
        Object[] actual = this.getAllDataProfile().toArray();
        Object[] expect = Constant.PRESET_DATA_PROFILE;
        for (int i = 0; i < actual.length; i++){
            Assert.assertEquals(actual[i],expect[i]);
        }
    }

    public void isPresetDataSortAlphabetically() {
        Object[] actual = this.getAllDataProfile().toArray();
        List<String> temp = this.getAllDataProfile();
        Collections.sort(temp);
        Object[] expect = temp.toArray();
        Assert.assertEquals(actual, expect);
    }

}
