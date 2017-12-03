package pl.com.bottega.hrs.model.commands;

import pl.com.bottega.hrs.application.users.Role;

import java.util.Set;

public class UpdateUserCommand implements Command {

    private Integer userNo;

    private String login, password, repeatedPassword;

    private Set<Role> roles;


    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void validate(ValidationErrors errors){
        validatePasswordLength(errors, password);
        validateRepeatedPassword(errors, password, repeatedPassword);
        if (roles != null && roles.isEmpty())
            errors.add("roles", "Role can't be blank");
    }


}
