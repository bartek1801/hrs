package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.AddDepartmentAssignmentCommand;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class DepartmentAssignmentHandler {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DepartmentAssignmentHandler(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
}

    @Transactional
    public void handle(AddDepartmentAssignmentCommand cmd){
        Department department = departmentRepository.get(cmd.getDeptNo());
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.assignDepartment(department);
        employeeRepository.save(employee);
    }
}
