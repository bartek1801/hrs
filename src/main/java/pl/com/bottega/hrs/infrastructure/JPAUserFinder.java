package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.application.UserDto;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Component
public class JPAUserFinder implements UserFinder {

    private EntityManager entityManager;

    public JPAUserFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public UserDto getUserDetails(Integer userNo) {
        User user = entityManager.find(User.class, userNo);
        if (user == null)
            throw new NoSuchEntityException();
        return new UserDto(user);
    }

    @Override
    @Transactional
    public UserDto getUserDetails(String login) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login");
        query.setParameter("login", login);
        User user = (User) query.getSingleResult();
        if (user == null)
            throw new NoSuchEntityException();
        return new UserDto(user);
    }


}
