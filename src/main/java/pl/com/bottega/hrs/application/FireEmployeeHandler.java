package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class FireEmployeeHandler {

    private EmployeeRepository employeeRepository;

    public FireEmployeeHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(FireEmployeeCommand cmd){
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.fire();
    }

}
