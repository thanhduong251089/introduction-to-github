package core.Models;

public class PageModel {

    private String pageName;
    private String parentPage;
    private String numOfCol;
    private String disAfter;
    private Boolean isPublic;


    public PageModel(String pageName, String parentPage, String numOfCol, String disAfter, Boolean isPublic){
        setPageName(pageName);
        setParentPage(parentPage);
        setNumOfCol(numOfCol);
        setDisAfter(disAfter);
        setPublic(isPublic);
    }

    public void setPublic(Boolean aPublic) {
        this.isPublic = aPublic;
    }

    public void setDisAfter(String disAfter) {
        this.disAfter = disAfter;
    }

    public void setNumOfCol(String numOfCol) {
        this.numOfCol = numOfCol;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setParentPage(String parentPage) {
        this.parentPage = parentPage;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public String getDisAfter() {
        return disAfter;
    }

    public String getNumOfCol() {
        return numOfCol;
    }

    public String getPageName() {
        return pageName;
    }

    public String getParentPage() {
        return parentPage;
    }
}
