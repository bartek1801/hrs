package pl.com.bottega.hrs;

import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.StandardTimeProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EntityManagerTest {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("HRS-TEST");
    }

    @After
    public void cleanUp(){
        executeInTransaction((em) -> {
            em.createNativeQuery("DELETE FROM  employees").executeUpdate();
        });
    }

    public static void executeInTransaction(Consumer<EntityManager> consumer){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.close(); // można zamiennie em.flush() lub em.close() robi flush i zamyka EntityManagera
        em.getTransaction().commit();
    }

    private Employee createEmployee(String firstName){
        Address address = new Address("al. Warszawska 100", "Lublin");
        return new Employee(1, firstName, "Nowak", LocalDate.now(), address, new StandardTimeProvider());
    }

    private void updateFirstName(String newName, Employee employee){
        employee.updateProfile(newName, "Nowak", LocalDate.now());
    }

    @Test
    public void trackChangesToEntities(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            updateFirstName("Janusz",employee);
            //em.detach(employee); // odłączanie od śledzenia
        });

        //then
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Janusz", employee.getFirstName());
        });

    }

    @Test
    public void mergesEntities(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janusz");
            Employee employeeCopy = em.merge(employee);
            updateFirstName("Eustachy", employeeCopy);
            updateFirstName("Stefan", employee);
        });

        //then
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Eustachy", employee.getFirstName());
        });

    }

    @Test
    public void removesEntities(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            em.remove(employee);
        });

        //then
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertNull(employee);
        });
    }

    @Test
    public void cascadesOperations(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        //then
        executeInTransaction((em) -> {
            Address address = em.find(Address.class, 1);
            assertNotNull(address);
        });
    }


    private Employee tmpEmployee;
    @Test(expected = LazyInitializationException.class)
    public void throwsLazyInitExeption(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        executeInTransaction((em) -> {
            tmpEmployee = em.find(Employee.class, 1);
        });

        tmpEmployee.getSalaries().size();

    }

}
