package pl.com.bottega.hrs.acceptance;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AcceptanceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate tt;

    @After
    public void cleanUp() {
        tt.execute((c) -> {
            em.createNativeQuery("DELETE FROM salaries").executeUpdate();
            em.createNativeQuery("DELETE FROM titles").executeUpdate();
            em.createNativeQuery("DELETE FROM dept_emp").executeUpdate();
            em.createNativeQuery("DELETE FROM departments").executeUpdate();
            em.createNativeQuery("DELETE FROM employees").executeUpdate();
            em.createNativeQuery("DELETE FROM users").executeUpdate();
            return null;
        });
    }

}
