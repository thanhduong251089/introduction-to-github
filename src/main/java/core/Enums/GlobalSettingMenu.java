package core.Enums;

public enum GlobalSettingMenu {
    ADD_PAGE ("Add Page"),
    DELETE("Delete");

    private final String text;

    GlobalSettingMenu(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString() { return text; }
}
