package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;


public class AddDepartmentAssignmentHandler {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public AddDepartmentAssignmentHandler(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
}

    public void handle(AddDepartmentCommand cmd){
        Department department = departmentRepository.get(cmd.getDeptNo());
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.assignDepartment(department);
        employeeRepository.save(employee);
    }
}
