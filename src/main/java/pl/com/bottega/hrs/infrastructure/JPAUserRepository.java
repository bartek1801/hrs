package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Component
public class JPAUserRepository implements UserRepository {

    private EntityManager entityManager;

    public JPAUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean isLoginOccupied(String login) {
        return get(login).isPresent();
    }

    @Override
    public Optional<User> get(String login) {
        try {
            User user = (User) entityManager.createQuery("FROM User u WHERE u.login = :login").
                    setParameter("login", login).getSingleResult();
            return Optional.of(user);
        }
        catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public User get(Integer userId) {
        User user = entityManager.find(User.class, userId);
        if(user == null)
            throw new NoSuchEntityException();
        return user;
    }

    @Override
    public User get(String login, String password) {
        try {
            User user = (User) entityManager.createQuery("FROM User u WHERE u.login = :login AND u.password = :password").
                    setParameter("login", login).
                    setParameter("password", password).
                    getSingleResult();
            return user;
        }catch (NoResultException ex){
            throw new NoSuchEntityException();
        }
    }
}
