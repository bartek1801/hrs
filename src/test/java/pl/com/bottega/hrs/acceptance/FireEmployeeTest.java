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
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FireEmployeeTest {

    @Autowired
    private FireEmployeeHandler fireEmployeeHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Test
    public void shouldFireEmployee(){
        //given
        AddDepartmentCommand addDepartmentCommand = new AddDepartmentCommand();
        addDepartmentCommand.setName("Marketing");
        addDepartmentCommand.setNumber("d1");
        addDepartmentHandler.handle(addDepartmentCommand);
        AddEmployeeCommand addEmployeeCommand = new AddEmployeeCommand();
        addEmployeeCommand.setFirstName("Janek");
        addEmployeeCommand.setLastName("Nowak");
        addEmployeeCommand.setAddress(new Address("TestStreet", "TestCity"));
        addEmployeeCommand.setBirthDate(LocalDate.parse("1990-01-01"));
        addEmployeeCommand.setDeptNo("d1");
        addEmployeeCommand.setGender(Gender.M);
        addEmployeeCommand.setSalary(50000);
        addEmployeeCommand.setTitle("Junior Developer");
        addEmployeeHandler.handle(addEmployeeCommand);

        //when
        FireEmployeeCommand fireEmployeeCommand = new FireEmployeeCommand();
        fireEmployeeCommand.setEmpNo(1);
        fireEmployeeHandler.handle(fireEmployeeCommand);

        //then
        DetailedEmployeeDto empDto = employeeFinder.getEmployeeDetails(1);

        assertFalse(empDto.getSalary().isPresent());
        assertFalse(empDto.getTitle().isPresent());
        assertFalse(empDto.getTitle().isPresent());

    }

}
