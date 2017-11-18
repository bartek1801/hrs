package pl.com.bottega.hrs.acceptance;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.infrastructure.JPAEmployeeRepository;
import pl.com.bottega.hrs.infrastructure.StandardTimeProvider;
import pl.com.bottega.hrs.model.*;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.ChangeSalaryCommand;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChangeEmployeeSalaryTest {

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private ChangeSalaryHandler changeSalaryHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

//    @Autowired
//    private TimeProvider timeProvider;


    private final TimeMachine timeMachine = new TimeMachine();


    @Test
    public void shouldChangeSalaryForEmployee(){
        //given
        //timeMachine.travel(Duration.ofDays(-365));
        AddDepartmentCommand addDepartmentCommand = new AddDepartmentCommand();
        addDepartmentCommand.setName("Marketing");
        addDepartmentCommand.setNumber("d1");
        addDepartmentHandler.handle(addDepartmentCommand);
        AddEmployeeCommand addEmployeeCommand = new AddEmployeeCommand();
        addEmployeeCommand.setFirstName("Jan");
        addEmployeeCommand.setLastName("Nowakowski");
        addEmployeeCommand.setAddress(new Address("testStreet", "testCity"));
        addEmployeeCommand.setBirthDate(LocalDate.parse("1990-01-18"));
        addEmployeeCommand.setDeptNo("d1");
        addEmployeeCommand.setGender(Gender.M);
        addEmployeeCommand.setSalary(25000);
        addEmployeeCommand.setTitle("Expert");
        addEmployeeHandler.handle(addEmployeeCommand);


        //when
        //timeMachine.travel(Duration.ofDays(200));
        ChangeSalaryCommand command1 = new ChangeSalaryCommand();
        command1.setEmpNo(1);
        command1.setAmount(50000);
        changeSalaryHandler.handle(command1);


        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Integer.valueOf(50000), employeeDto.getSalary().get());
//        assertEquals(Integer.valueOf(25000) , employeeDto.getSalaryHistory().get(0).getSalary());
//        assertEquals(Integer.valueOf(50000) , employeeDto.getSalaryHistory().get(1).getSalary());


    }


}
