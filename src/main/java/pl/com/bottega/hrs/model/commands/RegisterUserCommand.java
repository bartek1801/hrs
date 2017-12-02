package pl.com.bottega.hrs.model.commands;

public class RegisterUserCommand implements Command {

    private String login, password, repeatedPassword;

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
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

    public void validate(ValidationErrors errors){
        validatePresence(errors, "login", login );
        validatePresence(errors, "password", password);
        validatePresence(errors, "repeatedPassword", repeatedPassword);
        validateLogin(errors, login);
        validatePasswordLength(errors, password);
        validateRepeatedPassword(errors, password, repeatedPassword);
//        validatePasswordLength(errors);
//        validateRepeatedPassword(errors);
    }

    private void validateRepeatedPassword(ValidationErrors errors) {
        if (!password.equals(repeatedPassword))
            errors.add("password", "password and repeated password should be the same");
    }

    private void validatePasswordLength(ValidationErrors errors) {
        if (password.length() < 6)
            errors.add("password", "password should contain at least 6 characters");
    }


}
