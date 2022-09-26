package core.Models;

import core.Enums.Accounts;

public class AccountModel {
    private String username;
    private String password;

    public AccountModel(Accounts username, Accounts password) {
        setUsername(username.toString());
        setPassword(password.toString());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
