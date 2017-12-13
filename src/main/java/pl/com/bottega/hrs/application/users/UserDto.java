package pl.com.bottega.hrs.application.users;

import java.util.Set;

public class UserDto {

    Integer userNo;

    String login;

    Set<Role> roles;

    public UserDto(User user) {
        this.userNo = user.getId();
        this.login = user.getLogin();
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
