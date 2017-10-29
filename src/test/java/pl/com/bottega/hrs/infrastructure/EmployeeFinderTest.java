package pl.com.bottega.hrs.infrastructure;

import org.junit.Test;
import pl.com.bottega.hrs.application.BasicEmployeeDto;
import pl.com.bottega.hrs.application.EmployeeFinder;
import pl.com.bottega.hrs.application.EmployeeSearchCriteria;
import pl.com.bottega.hrs.application.EmployeeSearchResults;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.StandardTimeProvider;
import pl.com.bottega.hrs.model.TimeMachine;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EmployeeFinderTest extends InfrastructureTest {

    private int number = 1;
   // private final EmployeeFinder employeeFinder = new JPQLEmployeeFinder(createEntityManager());
    private final EmployeeFinder employeeFinder = new JPACriteriaEmployeeFinder(createEntityManager());
    private final EmployeeSearchCriteria criteria = new EmployeeSearchCriteria();
    //private final TimeMachine timeMachine = new TimeMachine();
    private EmployeeSearchResults results;

    @Test
    public void shouldFindByLastNameQuery() {
        //given
        createEmployee("Nowak");
        createEmployee("Nowacki");
        createEmployee("Kowalski");

        //when
        criteria.setLastNameQuery("nOwa");
        search();

        //then
        assertLastNames("Nowak", "Nowacki");

    }



    @Test
    public void shouldFindByFirstAndNameQuery() {
        //given
        createEmployee("Jan", "Nowak");
        createEmployee("Stefan", "Nowacki");
        createEmployee("Kowalski");

        //when
        criteria.setLastNameQuery("nOwa");
        criteria.setFirstNameQuery("Ja");
        search();

        //then
        assertLastNames("Nowak");

    }

    @Test
    public void shouldFindByFirstQuery() {
        //given
        createEmployee("Nowak");
        createEmployee("Nowacki");
        createEmployee("Kowalski");

        //when
        criteria.setFirstNameQuery("cze");
        search();

        //then
        assertLastNames("Nowak", "Nowacki", "Kowalski");
    }

    @Test
    public void shouldFindByFirstNameAndLastNameAndBirthDate(){
        //given
        createEmployee("Stefan", "Nowakowicz", LocalDate.parse("1980-01-05"));
        createEmployee("Jan", "Nowak", LocalDate.parse("1990-01-01"));
        createEmployee("JAndrzej", "Nowy", LocalDate.parse("1995-01-01"));
        createEmployee("Janusz", "Nowacki", LocalDate.parse("2000-01-05"));

        //when
//        criteria.setFirstNameQuery("J");
//        criteria.setLastNameQuery("now");
        criteria.setBirthDateFrom(LocalDate.parse("1990-01-01"));
        criteria.setBirthDateTo(LocalDate.parse("2017-01-01"));
        search();

        //then
        assertLastNames("Nowak","Nowy", "Nowacki");
    }

//    @Test
//    public void shouldFindByFirstNameAndLastNameAndBirthDateAndHireDate(){
//        //given
//        timeMachine.travel(Duration.ofDays(-365 * 3));
//        LocalDate t0 = timeMachine.today();
//        createEmployee("Stefan", "Nowakowicz", LocalDate.parse("1980-01-05"));
//        createEmployee("Jan", "Nowak", LocalDate.parse("1990-01-01"));
//        createEmployee("Andrzej", "Nowy", LocalDate.parse("1995-01-01"));
//        createEmployee("Janusz", "Nowacki", LocalDate.parse("2000-01-05"));
//
//        //when
//        criteria.setFirstNameQuery("J");
//        criteria.setLastNameQuery("now");
//        criteria.setBirthDateFrom(LocalDate.parse("1990-01-01"));
//        criteria.setBirthDateTo(LocalDate.parse("2017-01-01"));
//        search();
//
//        //then
//        assertLastNames("Nowak", "Nowacki");
//    }

    @Test
    public void shouldPaginateResults(){
        //given
        createEmployee("Nowak");
        createEmployee("Nowacki");
        createEmployee("Kowalski");
        createEmployee("Kowalewski");
        createEmployee("Kowalewska");

        //when
        criteria.setPageSize(2);
        criteria.setPageNumber(2);
        criteria.setFirstNameQuery("Cze");
        search();

        //then
        assertLastNames("Kowalski", "Kowalewski");
        assertEquals(5, results.getTotalCount());
        assertEquals(3, results.getPagesCount());
        assertEquals(2, results.getPageNumber());

    }

    //@Test
    public void shouldSearchBySalary(){
        //given
        Employee nowak = createEmployee("Nowak");
        Employee nowacki = createEmployee("Nowacki");
        createEmployee("Kowalski");

        executeInTransaction(entityManager -> {
            nowak.changeSalary(50000);
            entityManager.merge(nowak);
        });
        executeInTransaction(entityManager -> {
            nowacki.changeSalary(20000);
            entityManager.merge(nowacki);
        });

        //when
        criteria.setSalaryFrom(45000);
        criteria.setSalaryTo(60000);
        search();

        //then
        assertLastNames("Nowak");


    }




    private void search() {
        results = employeeFinder.search(criteria);
    }

    private void assertLastNames(String... lastNames) {
        assertEquals(Arrays.asList(lastNames),
                results.getResults().stream()
                        .map(BasicEmployeeDto::getLastName).collect(Collectors.toList())
        );
    }

    private Employee createEmployee(String firstName, String lastName, LocalDate birthDate) {
        Address address = new Address("al. Warszawska 10", "Lublin");
        Employee employee = new Employee(number++, firstName, lastName, birthDate, address, new StandardTimeProvider());
        executeInTransaction((em) -> {
            em.persist(employee);
        });
        return employee;

    }

    private Employee createEmployee(String firstName, String lastName) {
        Address address = new Address("al. Warszawska 10", "Lublin");
        Employee employee = new Employee(number++, firstName, lastName, LocalDate.now(), address, new StandardTimeProvider());
        executeInTransaction((em) -> {
            em.persist(employee);
        });
        return employee;
    }


    private Employee createEmployee(String lastName) {
        Address address = new Address("al. Warszawska 10", "Lublin");
        Employee employee = new Employee(number++, "Czesiek", lastName, LocalDate.now(), address, new StandardTimeProvider());
        executeInTransaction((em) -> {
            em.persist(employee);
        });
        return employee;
    }


}
