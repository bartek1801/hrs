package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.TimeMachine;
import pl.com.bottega.hrs.model.commands.AddDepartmentAssignmentCommand;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.UnassignDepartmentCommand;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssignDepartmentTest {

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private DepartmentAssignmentHandler departmentAssignmentHandler;

    @Autowired
    private UnassignDepartmentHandler unassignDepartmentHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Test
    public void shouldAssignAndUnassignDepartment(){
        //given
        AddDepartmentCommand cmd1 = new AddDepartmentCommand();
        cmd1.setName("Marketing");
        cmd1.setNumber("d1");
        addDepartmentHandler.handle(cmd1);
        AddDepartmentCommand cmd2 = new AddDepartmentCommand();
        cmd2.setName("Sales");
        cmd2.setNumber("d2");
        addDepartmentHandler.handle(cmd2);
        AddDepartmentCommand cmd3 = new AddDepartmentCommand();
        cmd3.setName("Accounting");
        cmd3.setNumber("d3");
        addDepartmentHandler.handle(cmd3);

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
        AddDepartmentAssignmentCommand cmd4 = new AddDepartmentAssignmentCommand();
        cmd4.setEmpNo(1);
        cmd4.setDeptNo("d2");
        cmd4.setDeptName("Sales");
        departmentAssignmentHandler.handle(cmd4);
        AddDepartmentAssignmentCommand cmd5 = new AddDepartmentAssignmentCommand();
        cmd5.setEmpNo(1);
        cmd5.setDeptNo("d3");
        cmd5.setDeptName("Account");
        departmentAssignmentHandler.handle(cmd5);
        UnassignDepartmentCommand cmd6 = new UnassignDepartmentCommand();
        cmd6.setEmpNo(1);
        cmd6.setDeptNo("d2");
        cmd6.setDeptName("Sales");
        unassignDepartmentHandler.handle(cmd6);


        //then
        DetailedEmployeeDto empDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Arrays.asList("d1", "d3"), empDto.getDeptNo());

    }


}
