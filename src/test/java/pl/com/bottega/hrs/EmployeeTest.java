package pl.com.bottega.hrs;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Salary;
import pl.com.bottega.hrs.model.Title;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmployeeTest {

    private static EntityManagerFactory emf;


    @BeforeClass
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("HRS-TEST");
    }


    public static void executeInTransaction(Consumer<EntityManager> consumer){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.close();
        em.getTransaction().commit();
    }

    public static Employee createEmploye(Integer empNo, String firstName, String lastName){
        Address address = new Address( "Kraśnicka", "Lublin");
        return new Employee(empNo, firstName, lastName, LocalDate.parse("1990-03-02"), address);
    }





    private Employee testEmployee1;
    @Test
    public void shouldGetCurrentSalary(){
        //given
        executeInTransaction((entityManager) -> {
            testEmployee1 = createEmploye(1, "Jan", "Nowak");
            Salary.SalaryId salaryId1 = new Salary.SalaryId(1, LocalDate.parse("2015-03-02"));
            Salary salaryEmp1No1 = new Salary(salaryId1, 10000,  LocalDate.parse("2016-04-12"));
            testEmployee1.addSalary(salaryEmp1No1);
            Salary.SalaryId salaryId2 = new Salary.SalaryId(1, LocalDate.parse("2016-04-12"));
            Salary salaryEmp1No2 = new Salary(salaryId2, 12500, LocalDate.parse("2017-03-02"));
            testEmployee1.addSalary(salaryEmp1No2);
            Salary.SalaryId salaryId3 = new Salary.SalaryId(1, LocalDate.parse("2017-03-02"));
            Salary salaryEmp1No3 = new Salary(salaryId3, 22599, LocalDate.parse("9999-01-01"));
            testEmployee1.addSalary(salaryEmp1No3);
            entityManager.persist(testEmployee1);
        } );


        //then
        executeInTransaction(entityManager -> {
            //Employee employee = entityManager.find(Employee.class, 10005);
            Optional<Integer> salary = testEmployee1.getCurrentSalary();
            //assertEquals(Optional.of(94692), salary);
            assertEquals(Optional.of(22599), salary);
        });
    }

    private Employee testEmployee2;
    @Test
    public void shouldGetNullSalary(){

        //given
        executeInTransaction((entityManager) -> {
            testEmployee2 = createEmploye(2, "Janek", "Kowalski");
            entityManager.persist(testEmployee2);
        });

        //then
        executeInTransaction(entityManager -> {
            //Employee employee = entityManager.find(Employee.class, 10001);
           // Employee employee = entityManager.find(Employee.class, 2);
            Optional<Integer> salary = testEmployee2.getCurrentSalary();
            assertNull(salary);
        });
    }

    private Employee testEmployee3;
    @Test
    public void shouldAddSalaryWhenThereIsNoSalary(){
        //and shuoldAddTitleWhenThereIsNoTitle()

        //given
        executeInTransaction((entityManager) -> {
            testEmployee3 = createEmploye(3, "Janek", "Kowalski");
        } );

        //when
        executeInTransaction((entityManager) ->{
            //testEmployee = entityManager.find(Employee.class, 2);
            testEmployee3.changeSalary(1234);
            testEmployee3.changeTitle("Dyrektor");
            //entityManager.persist(testEmployee3);
        } );


       //then
        Optional<Integer> salary = testEmployee3.getCurrentSalary();
        String title = testEmployee3.getCurrentTitle();
        assertEquals(Optional.of(1234), salary);
        assertEquals("Dyrektor", title);
    }


    private Employee testEmployee4;
    @Test
    public void shouldAddNewSalaryWhenSalaryAlreadyExist(){
        //given
        executeInTransaction((entityManager) -> {
            testEmployee4 = createEmploye(4, "Janusz", "Nowak");
            Salary.SalaryId salaryId1 = new Salary.SalaryId(4, LocalDate.parse("2015-03-02"));
            Salary salaryEmp1No1 = new Salary(salaryId1, 10000,  LocalDate.parse("2016-04-12"));
            testEmployee4.addSalary(salaryEmp1No1);
            Salary.SalaryId salaryId2 = new Salary.SalaryId(4, LocalDate.parse("2016-04-12"));
            Salary salaryEmp1No2 = new Salary(salaryId2, 12500, LocalDate.parse("2017-03-02"));
            testEmployee4.addSalary(salaryEmp1No2);
            Salary.SalaryId salaryId3 = new Salary.SalaryId(4, LocalDate.parse("2017-03-02"));
            Salary salaryEmp1No3 = new Salary(salaryId3, 22599, LocalDate.parse("9999-01-01"));
            testEmployee4.addSalary(salaryEmp1No3);
            entityManager.persist(testEmployee4);
        } );


        //when
        executeInTransaction(entityManager -> {
            testEmployee4.changeSalary(123000);
            //entityManager.persist(testEmployee4);
        });

        //when
        Optional<Integer> salary = testEmployee4.getCurrentSalary();
        assertEquals(Optional.of(123000) ,salary);
    }

    private Employee testEmployee5;
    @Test
    public void shuldGetCurrentTitle(){
        //given
        executeInTransaction(entityManager -> {
         testEmployee5 = createEmploye(5, "Grażyna", "Nowak");
         Title.TitleId titleId1 = new Title.TitleId(5, "Kierownik", LocalDate.parse("2014-01-05"));
         Title title1 = new Title(titleId1, LocalDate.parse("2015-05-09"));
         testEmployee5.addTitle(title1);
         Title.TitleId titleId2 = new Title.TitleId(5, "Dyrektor", LocalDate.parse("2015-05-09"));
         Title title2 = new Title(titleId2, LocalDate.parse("9999-01-01"));
         testEmployee5.addTitle(title2);
         entityManager.persist(testEmployee5);
        });

        //then
        executeInTransaction(entityManager -> {
            String title = testEmployee5.getCurrentTitle();
            assertEquals("Dyrektor", title);
        });
    }

    private Employee testEmployee6;
    @Test
    public void shouldGetNullTitle(){
        //given
        executeInTransaction(entityManager -> {
            testEmployee6 = createEmploye(6, "Kapitan", "Bomba");
            entityManager.persist(testEmployee6);
        });

        //then
        executeInTransaction(entityManager -> {
            String title = testEmployee6.getCurrentTitle();
            assertNull(title);
        });
    }

    private Employee testEmployee7;
    @Test
    public void shouldAddNewTitleWhenTitleAlreadyExist(){
        //given
        executeInTransaction(entityManager -> {
            testEmployee7 = createEmploye(7, "Kuba", "Kuba");
            Title.TitleId titleId1 = new Title.TitleId(7, "Specjalista", LocalDate.parse("2014-01-05"));
            Title title1 = new Title(titleId1, LocalDate.parse("2015-05-09"));
            testEmployee7.addTitle(title1);
            Title.TitleId titleId2 = new Title.TitleId(7, "Kierownik", LocalDate.parse("2015-05-09"));
            Title title2 = new Title(titleId2, LocalDate.parse("9999-01-01"));
            testEmployee7.addTitle(title2);
            entityManager.persist(testEmployee7);

        });

        //when
        executeInTransaction(entityManager -> {
            testEmployee7.changeTitle("Dyrektor");
        });

        //then
        executeInTransaction(entityManager -> {
            String title = testEmployee7.getCurrentTitle();
            assertEquals("Dyrektor", title);
        });
    }


}
