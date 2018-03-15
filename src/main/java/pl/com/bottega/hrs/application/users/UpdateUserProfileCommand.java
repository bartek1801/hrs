package pl.com.bottega.hrs.application.users;

import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

import java.util.Set;

public class UpdateUserProfileCommand implements Command {

    private Integer userId;
    private String login, newPassword, repeatedPassword;
    private Set<Role> roles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "userId", userId);
        validateFormat(errors, "login", login, "^[\\w\\d]+$");
        if(newPassword != null && !newPassword.equals(repeatedPassword)) {
            errors.add("newPassword", "passwords mismatch");
            errors.add("repeatedPassword", "passwords mismatch");
        }
        validateMinLength(errors, "password", newPassword, 6);
        validateMinLength(errors, "repeatedPassword", repeatedPassword, 6);
        if(roles != null && roles.size() == 0) {
            errors.add("roles", "at least one role required");
        }
    }
}
