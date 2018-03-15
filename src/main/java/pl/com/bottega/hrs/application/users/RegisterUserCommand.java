package pl.com.bottega.hrs.application.users;

import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

public class RegisterUserCommand implements Command {

    private String login, password, repeatedPassword;

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

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "login", login);
        validatePresence(errors, "password", password);
        validatePresence(errors, "repeatedPassword", repeatedPassword);
        validateFormat(errors, "login", login, "^[\\w\\d]+$");
        validateMinLength(errors, "password", password, 6);
        validatePasswordsMatch(password, repeatedPassword, errors);
    }

    private void validatePasswordsMatch(String password, String repeatedPassword, ValidationErrors errors) {
        if (password != null && repeatedPassword != null && !password.equals(repeatedPassword)) {
            errors.add("password", "passwords mismatch");
            errors.add("repeatedPassword", "passwords mismatch");
        }
    }
}
