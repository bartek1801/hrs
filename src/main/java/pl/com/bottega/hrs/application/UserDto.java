package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;

import java.util.Set;

public class UserDto {

    Integer userNo;

    String login, password;

    Set<Role> roles;

    public UserDto(User user) {
        this.userNo = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
