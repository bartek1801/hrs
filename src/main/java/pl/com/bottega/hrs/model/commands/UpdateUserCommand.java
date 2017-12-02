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

//    Zasady działania:
//    Nowy login trzeba sprawdzić pod kątem zajętości przez innego usera
//    Zasady co do walidacji hasła j.w.
//    Każdy z parametrów jest opcjonalny tzn. można zmienić tylko login i pominąć resztę parametrów.
//    Wówczas pozostałe pola mogą nie znaleźć się w requeście.
//    W przypadku zmiany hasła zawsze należy podać dwa pola new i repeatedPassword
//    W przypadku zmiany ról, nie można podać pustej tablicy.
//    Dodaj walidację komendy zgodnie z w.w. zasadami.

    public void validate(ValidationErrors errors){
        validatePasswordLength(errors, password);
        validateRepeatedPassword(errors, password, repeatedPassword);
        if (roles.isEmpty())
            errors.add("roles", "Role can't be blank");

    }


}
