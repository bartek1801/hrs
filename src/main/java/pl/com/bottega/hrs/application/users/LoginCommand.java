package pl.com.bottega.hrs.application.users;

import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

public class LoginCommand implements Command {

    private String login, password;

    public void validate(ValidationErrors validationErrors){
        validatePresence(validationErrors, "login", login);
        validatePresence(validationErrors, "password", password);
    }

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
}
