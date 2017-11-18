package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateEmployeeTest {

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Test
    public void shouldCreateEmployeeWithAllDetails(){
        //given
        AddDepartmentCommand addDepartmentCommand = new AddDepartmentCommand();
        addDepartmentCommand.setName("Marketing");
        addDepartmentCommand.setNumber("d1");
        addDepartmentHandler.handle(addDepartmentCommand);

        //when
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

        //then
        DetailedEmployeeDto empDto = employeeFinder.getEmployeeDetails(1);
        assertEquals("Janek", empDto.getFirstName());
        assertEquals("Nowak", empDto.getLastName());
        assertEquals( "TestStreet", empDto.getAddress().getStreet());
        assertEquals("TestCity", empDto.getAddress().getCity());
        assertEquals(LocalDate.parse("1990-01-01"), empDto.getBirthDate());
        assertEquals(Arrays.asList("d1"), empDto.getDeptNo());
        assertEquals(Gender.M, empDto.getGender());
        assertEquals(Integer.valueOf(50000), empDto.getSalary().get());
        assertEquals("Junior Developer", empDto.getTitle().get());


    }




}
