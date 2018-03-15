package pl.com.bottega.hrs.application.users;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String login;

    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        roles.add(Role.STANDARD);
    }

    public String getLogin() {
        return login;
    }

    public void updateProfile(UpdateUserProfileCommand command) {
        if (command.getLogin() != null)
            login = command.getLogin();
        if (command.getNewPassword() != null)
            password = command.getNewPassword();
        if (command.getRoles() != null) {
            roles.clear();
            roles.addAll(command.getRoles());
        }
    }

    public Integer getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public boolean hasRoles(Role[] requiredRoles) {
        return roles.containsAll(Arrays.stream(requiredRoles).collect(Collectors.toList()));
    }
}
