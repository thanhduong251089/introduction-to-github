package core.Enums;

public enum HeadMenu {
    ADMINISTRATOR("administrator"),
    ADMINISTER("Administer"),
    HELP("Help"),
    LAB_MANAGER("Lab Manager"),
    REPOSITORY("Repository: ");

    private final String text;

    HeadMenu(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString() { return text; }
}
