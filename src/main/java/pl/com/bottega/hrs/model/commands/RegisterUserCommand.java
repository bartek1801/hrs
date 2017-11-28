package pl.com.bottega.hrs.model.commands;

public class RegisterUserCommand implements Command {

    String login, password, repeatedPassword;

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
        if (password.length() < 6)
            errors.add("password", "password should contain at least 6 characters");
        if (!password.equals(repeatedPassword))
            errors.add("password", "password and repeated password should be the same");
    }

    private void validateLogin(ValidationErrors errors, String login) {
        if(!validateMarks(login))
            errors.add("login", "login should contains only letters and numbers");
    }

    private boolean validateMarks(String login){
        String acceptableMarks = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Integer checkSum = 0;
        for (int i = 0; i < login.length(); i++){
            for (int k = 0; k < acceptableMarks.length();k++){
                if (login.charAt(i) == acceptableMarks.charAt(k) )
                    checkSum += 1;
            }
        }
        return checkSum == login.length();
    }
}
