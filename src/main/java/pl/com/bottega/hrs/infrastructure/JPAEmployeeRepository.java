package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Component
public class JPAEmployeeRepository implements EmployeeRepository {

    private EntityManager entityManager;

    public JPAEmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer generateNumber() {
        Query query = entityManager.createQuery("SELECT MAX(e.id) FROM Employee e");
        Integer result = (Integer) query.getSingleResult();
        if (result == null)
            return 1;
        return result + 1;
    }

    @Override
    public void save(Employee employee) {
        //entityManager.persist(employee);
        entityManager.merge(employee);
    }

    @Override
    public Employee get(Integer empNo) {
        Employee employee = entityManager.find(Employee.class, empNo);
        if (employee == null)
            throw new NoSuchEntityException();
        return employee;
    }
}
