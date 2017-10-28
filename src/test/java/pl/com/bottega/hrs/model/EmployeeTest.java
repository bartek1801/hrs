package pl.com.bottega.hrs.model;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeTest {

    public static final int SALARY = 50000 * 12;
    private final Address address = new Address("Północna", "Lublin");
    private final TimeMachine timeMachine = new TimeMachine();
    private final Employee sut = new Employee(1,
            "Jan", "Nowak",
            LocalDate.parse("1960-01-01"),
            address,
            timeMachine);

    @Test
    public void shouldReturnNoSalaryIfNoSalaryDefined(){
        //then
        assertFalse(getCurrentSalary().isPresent());
    }


    @Test
    public void shouldAddAndReturnEmployeeSalary(){
        //when
        sut.changeSalary(SALARY);

        //then
        assertTrue(getCurrentSalary().isPresent());
        assertEquals(SALARY, getCurrentSalaryValue());
    }

    @Test
    public void shouldAllowMultipleChangesOfSalary(){
        //when
        sut.changeSalary(SALARY);
        sut.changeSalary(SALARY / 2);

        //then
        assertEquals(SALARY/2, getCurrentSalaryValue());
        assertEquals(1, sut.getSalaries().size());
    }

    @Test
    public void shouldKeepSalaryHistory(){
        //when
        timeMachine.travel(Duration.ofDays(-365 * 2));
        LocalDate t0 = timeMachine.today();
        sut.changeSalary(SALARY);
        timeMachine.travel(Duration.ofDays(365));
        LocalDate t1 = timeMachine.today();
        sut.changeSalary(SALARY / 2);
        timeMachine.travel(Duration.ofDays(100));
        LocalDate t2 = timeMachine.today();
        sut.changeSalary(SALARY * 2);

        //then
        Collection<Salary> history = sut.getSalaries();
        assertEquals(3, history.size());
        assertEquals(
                Arrays.asList(SALARY, SALARY / 2, SALARY * 2),
                history.stream().map((s) -> s.getValue()).collect(Collectors.toList())
        );
        assertEquals(
                Arrays.asList(t0, t1, t2),
                history.stream().map((s) -> s.getFromDate()).collect(Collectors.toList())
        );
        assertEquals(
                Arrays.asList(t1, t2, Constants.MAX_DATE),
                history.stream().map((s) -> s.getToDate()).collect(Collectors.toList()));
    }


    private int getCurrentSalaryValue() {
        return getCurrentSalary().get().getValue();
    }


    private Optional<Salary> getCurrentSalary() {
        return sut.getCurrentSalary();
    }

}
