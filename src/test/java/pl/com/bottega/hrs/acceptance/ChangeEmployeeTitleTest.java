package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.ChangeEmployeeTitleCommand;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChangeEmployeeTitleTest {

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private ChangeEmployeeTitleHandler changeEmployeeTitleHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Test
    public void shouldChangeEmployeeTitle(){
        //given
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
        ChangeEmployeeTitleCommand cmd = new ChangeEmployeeTitleCommand();
        cmd.setEmpNo(1);
        cmd.setTitle("Accountant");
        changeEmployeeTitleHandler.handle(cmd);

        //then
        DetailedEmployeeDto empDto = employeeFinder.getEmployeeDetails(1);
        assertEquals("Accountant", empDto.getTitle()); //TODO popraw test

    }

}
