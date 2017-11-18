package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.TimeProvider;
import pl.com.bottega.hrs.model.commands.EditEmployeeProfileCommand;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class EditEmployeeProfileHanlder {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private TimeProvider timeProvider;

    public void handle(EditEmployeeProfileCommand cmd){
        Employee employee = employeeRepository.get(cmd.getEmpNo());
    }

}
