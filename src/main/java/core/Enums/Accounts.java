package core.Enums;

public enum Accounts {
    VALID_USERNAME("administrator"),
    BLANK_USERNAME(""),
    VALID_PASSWORD(""),
    TEST_USERNAME("test"),
    TEST_PASSWORD("test"),
    TEST_PASSWORD_UPPERCASE("TEST"),
    INVALID_USERNAME("abc"),
    INVALID_PASSWORD("abc"),
    HUNGNGUYEN_USERNAME("hung.nguyen");

    private final String repository;

    Accounts(String value) {
        this.repository = value;
    }

    @Override
    public String toString() { return repository; }
}
