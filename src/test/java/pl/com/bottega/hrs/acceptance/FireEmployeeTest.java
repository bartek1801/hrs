package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.application.FireEmployeeHandler;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FireEmployeeTest  extends AcceptanceTest {

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private FireEmployeeHandler fireHandler;

    @Test
    public void shouldFireEmployee() {
        //given
        String deptNo = "d001";
        AddDepartmentCommand deptCmd = new AddDepartmentCommand();
        deptCmd.setNumber(deptNo);
        deptCmd.setName("Maintentance");
        addDepartmentHandler.handle(deptCmd);
        AddEmployeeCommand cmd = new AddEmployeeCommand();
        cmd.setFirstName("Janek");
        cmd.setLastName("Nowak");
        cmd.setAddress(new Address("Krancowa", "Lublin"));
        cmd.setBirthDate(LocalDate.parse("1980-01-01"));
        cmd.setGender(Gender.M);
        cmd.setSalary(500000);
        cmd.setTitle("Manager");
        cmd.setDeptNo(deptNo);
        addEmployeeHandler.handle(cmd);

        //when
        FireEmployeeCommand fireCmd = new FireEmployeeCommand();
        fireCmd.setEmpNo(1);
        fireHandler.handle(fireCmd);

        // then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(0, employeeDto.getDepartmentNumbers().size());
        assertFalse(employeeDto.getSalary().isPresent());
        assertFalse(employeeDto.getTitle().isPresent());


    }
}