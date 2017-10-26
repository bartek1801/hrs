package pl.com.bottega.hrs;

import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class App2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "HRS" );

        Address address = new Address("al. Warszawska 100", "Lublin");
        Employee employee = new Employee(500022, "Krzysz", "Jeżyna",  LocalDate.parse("1987-08-07"), address);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        //em.merge(employee);
        em.flush();

        em.getTransaction().commit();
        emf.close();
    }
}

