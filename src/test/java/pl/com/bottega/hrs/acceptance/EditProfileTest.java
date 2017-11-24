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
import pl.com.bottega.hrs.model.commands.ChangeEmployeeProfileCommand;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EditProfileTest  extends AcceptanceTest {


    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private ChangeEmployeeProfileHandler profileHandler;

    @Test
    public void shouldChangeEmployeeProfile() {
        //given
        String deptNo = "d001";
        AddDepartmentCommand deptCmd = new AddDepartmentCommand();
        deptCmd.setNumber(deptNo);
        deptCmd.setName("Maintentance");
        addDepartmentHandler.handle(deptCmd);

        //when
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


        ChangeEmployeeProfileCommand profileCmd = new ChangeEmployeeProfileCommand();
        profileCmd.setFirstName("Janina");
        profileCmd.setAddress(new Address("test street","test city"));
        profileCmd.setLastName("Nowacka");
        profileCmd.setBirthDate(LocalDate.parse("1981-01-01"));
        profileCmd.setEmpNo(1);
        profileCmd.setGender(Gender.F);
        profileHandler.handle(profileCmd);

        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals("Janina", employeeDto.getFirstName());
        assertEquals("Nowacka", employeeDto.getLastName());
        assertEquals(new Address("test street", "test city"), employeeDto.getAddress());
        assertEquals(LocalDate.parse("1981-01-01"), employeeDto.getBirthDate());
        assertEquals(Gender.F, employeeDto.getGender());
    }

}