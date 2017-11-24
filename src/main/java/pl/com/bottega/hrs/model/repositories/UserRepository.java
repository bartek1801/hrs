package pl.com.bottega.hrs.model.repositories;

import pl.com.bottega.hrs.application.users.User;

public interface UserRepository {

    Integer generateNumber();

    void save(User user);

    User getUser(Integer userNo);
}
