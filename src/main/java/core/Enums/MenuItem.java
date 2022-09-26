package core.Enums;

public enum MenuItem {
    MY_PROFILE("My Profile"),
    LOGOUT("Logout"),
    DATA_PROFILE ("Data Profiles");

    private final String text;

    MenuItem(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString() { return text; }
}
