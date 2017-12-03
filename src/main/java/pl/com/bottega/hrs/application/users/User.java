package pl.com.bottega.hrs.application.users;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String login;

    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id")
    )
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        roles.add(Role.STANDARD);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void updateProfile(String login, String password, Set<Role> roles) {
        if (login != null)
            this.login = login;
        if (password != null)
            this.password = password;
        if (roles != null)
            this.roles = roles;
            //roles.addAll(roles);
    }
}
