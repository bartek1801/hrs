package pl.com.bottega.hrs.application.users;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.infrastructure.NoSuchEntityException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class JPAUserRepository implements UserRepository {

    private EntityManager entityManager;

    public JPAUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer generateNumber() {
        Integer number = (Integer) entityManager.createQuery("SELECT MAX(u.id) + 1 FROM User u").getSingleResult();
        if (number == null)
            return 1;
        return number;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Integer userNo) {
        User user = entityManager.find(User.class, userNo);
        if (user == null)
            throw new NoSuchEntityException();
        return user;
    }

    @Override
    public boolean checkLoginAvailability(String login) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login");
        query.setParameter("login", login);
        List<User> result = query.getResultList();
        if (result.isEmpty())
            return true;
        return false;
    }
}
