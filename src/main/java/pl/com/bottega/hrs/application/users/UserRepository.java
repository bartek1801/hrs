package pl.com.bottega.hrs.application.users;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    boolean isLoginOccupied(String login);

    Optional<User> get(String login);

    User get(Integer userId);

    User get(String login, String password);
}
