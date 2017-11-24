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
import pl.com.bottega.hrs.model.commands.AssignDepartmentToEmployeeCommand;
import pl.com.bottega.hrs.model.commands.UnassignDepartmentCommand;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentAssignUnassignTest  extends AcceptanceTest {

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private DepartmentAssignmentHandler departmentAssignmentHandler;

    @Autowired
    private UnassignDepartmentHandler unassignDepartmentHandler;

    @Test
    public void shouldAssignDepartment() {
        //given
        String deptNo = "d001";
        AddDepartmentCommand deptCmd = new AddDepartmentCommand();
        deptCmd.setNumber(deptNo);
        deptCmd.setName("Maintentance");
        addDepartmentHandler.handle(deptCmd);
        deptCmd.setNumber("d002");
        deptCmd.setName("Engineering");
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
        AssignDepartmentToEmployeeCommand assingDeptCommand = new AssignDepartmentToEmployeeCommand();
        assingDeptCommand.setDeptNo("d002");
        assingDeptCommand.setEmpNo(1);
        departmentAssignmentHandler.handle(assingDeptCommand);

        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Arrays.asList("d001","d002"), employeeDto.getDepartmentNumbers());
    }

    @Test
    public void shouldUnassignDepartment() {
        //given
        String deptNo = "d001";
        AddDepartmentCommand deptCmd = new AddDepartmentCommand();
        deptCmd.setNumber(deptNo);
        deptCmd.setName("Maintentance");
        addDepartmentHandler.handle(deptCmd);
        deptCmd.setNumber("d002");
        deptCmd.setName("Engineering");
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
        AssignDepartmentToEmployeeCommand assingDeptCommand = new AssignDepartmentToEmployeeCommand();
        assingDeptCommand.setDeptNo("d002");
        assingDeptCommand.setEmpNo(1);
        departmentAssignmentHandler.handle(assingDeptCommand);

        //when
        UnassignDepartmentCommand unassignCmd = new UnassignDepartmentCommand();
        unassignCmd.setDeptNo("d001");
        unassignCmd.setEmpNo(1);
        unassignDepartmentHandler.handle(unassignCmd);

        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Arrays.asList("d002"), employeeDto.getDepartmentNumbers());

    }

}