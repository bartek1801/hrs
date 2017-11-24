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
import pl.com.bottega.hrs.model.commands.ChangeSalaryCommand;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChangeSalaryTest extends AcceptanceTest {

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private ChangeSalaryHandler changeSalaryHandler;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Test
    public void shouldChangeSalary() {
        //given
        String deptNo = "d001";
        AddDepartmentCommand deptCmd = new AddDepartmentCommand();
        deptCmd.setNumber(deptNo);
        deptCmd.setName("Maintentance");
        addDepartmentHandler.handle(deptCmd);
        AddEmployeeCommand cmd = new AddEmployeeCommand();
        cmd.setFirstName("Janek");
        cmd.setLastName("Nowak");
        cmd.setAddress(new Address("Krańcowa", "Lublin"));
        cmd.setBirthDate(LocalDate.parse("1980-01-01"));
        cmd.setGender(Gender.M);
        cmd.setSalary(500000);
        cmd.setTitle("Manager");
        cmd.setDeptNo(deptNo);
        addEmployeeHandler.handle(cmd);

        //when
        ChangeSalaryCommand salaryCommand = new ChangeSalaryCommand();
        salaryCommand.setAmount(200000);
        salaryCommand.setEmpNo(1);
        changeSalaryHandler.handle(salaryCommand);

        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Integer.valueOf(200000), employeeDto.getSalary().get());

    }
}