package pl.com.bottega.hrs.application.users;

import pl.com.bottega.hrs.application.users.User;

public interface UserRepository {

    Integer generateNumber();

    void save(User user);

    User getUser(Integer userNo);

    boolean checkLoginAvailability(String login);
}
