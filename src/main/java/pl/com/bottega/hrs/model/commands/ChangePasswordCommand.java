package pl.com.bottega.hrs.model.commands;

import pl.com.bottega.hrs.application.users.Role;

import java.util.Set;

public class ChangePasswordCommand implements Command {

    String login, password, repeatedPassword;

    Set<Role> roles;



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }




}
