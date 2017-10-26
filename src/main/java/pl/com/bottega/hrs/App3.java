package pl.com.bottega.hrs;

import pl.com.bottega.hrs.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App3 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.println("Podaj datę od. (rrrr-mm-dd)");
        LocalDate fromDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Podaj datę do...");
        LocalDate toDate = LocalDate.parse(scanner.nextLine());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRS");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("SELECT e FROM Employee e " +
                "WHERE e.lastName LIKE :lastName " +
                "AND (e.birthDate > :fromDate AND e.birthDate < :toDate) ").
                setParameter("lastName", lastName + "%").
                setParameter("fromDate", fromDate).
                setParameter("toDate", toDate).
                setMaxResults(20);

        List<Employee> result = query.getResultList();

        for (Employee emp : result){
            System.out.println(emp);
            // System.out.println(emp.getFirstName() + " " + emp.getLastName());
        }

        em.flush();
        em.getTransaction().commit();
        emf.close();
    }

}
