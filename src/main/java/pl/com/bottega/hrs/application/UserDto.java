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
}
