package pl.com.bottega.hrs.ui.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.infrastructure.Secured;
import pl.com.bottega.hrs.model.commands.*;

@RestController
@Secured
public class EmployeesController {

    private EmployeeFinder employeeFinder;
    private CommandGateway gateway;

    public EmployeesController(EmployeeFinder employeeFinder, CommandGateway gateway) {
        this.employeeFinder = employeeFinder;
        this.gateway = gateway;
    }

    @GetMapping("/employees/{empNo}")
    public DetailedEmployeeDto get(@PathVariable Integer empNo) {

        return employeeFinder.getEmployeeDetails(empNo);
    }

    @GetMapping("/employees")
    public EmployeeSearchResults get(EmployeeSearchCriteria criteria) {

        return employeeFinder.search(criteria);
    }

    @PutMapping("/employees/{empNo}/salary")
    public DetailedEmployeeDto changeSalary(@PathVariable Integer empNo, @RequestBody ChangeSalaryCommand cmd) {

        cmd.setEmpNo(empNo);
        gateway.execute(cmd);
        return employeeFinder.getEmployeeDetails(empNo);
    }

    @PutMapping("/employees/{empNo}/title")
    public DetailedEmployeeDto changeTitle(@PathVariable Integer empNo, @RequestBody ChangeEmployeeTitleCommand cmd) {

        cmd.setEmpNo(empNo);
        gateway.execute(cmd);
        return employeeFinder.getEmployeeDetails(empNo);
    }

    @PutMapping("/employees/{empNo}/department")
    public DetailedEmployeeDto assignToDepartment(@PathVariable Integer empNo,
                                                  @RequestBody AssignDepartmentToEmployeeCommand cmd) {

        cmd.setEmpNo(empNo);
        gateway.execute(cmd);
        return employeeFinder.getEmployeeDetails(empNo);
    }

    @DeleteMapping("/employees/{empNo}/department")
    public DetailedEmployeeDto unassignFromDepartment(@PathVariable Integer empNo,
                                                      @RequestBody UnassignDepartmentCommand cmd) {

        cmd.setEmpNo(empNo);
        gateway.execute(cmd);
        return employeeFinder.getEmployeeDetails(empNo);
    }

    @PutMapping("/employees/{empNo}")
    public DetailedEmployeeDto updateProfile(@PathVariable Integer empNo, @RequestBody ChangeEmployeeProfileCommand cmd) {
        cmd.setEmpNo(empNo);
        gateway.execute(cmd);
        return employeeFinder.getEmployeeDetails(empNo);
    }

    @PostMapping("/authenticate/{userName}")
    public void authenticate(String userName) {

    }


}