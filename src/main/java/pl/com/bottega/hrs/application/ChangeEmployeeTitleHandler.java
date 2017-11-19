package pl.com.bottega.hrs.application;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.ChangeEmployeeTitleCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class ChangeEmployeeTitleHandler implements Handler<ChangeEmployeeTitleCommand> {

    private EmployeeRepository employeeRepository;

    public ChangeEmployeeTitleHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(ChangeEmployeeTitleCommand cmd) {
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.changeTitle(cmd.getTitle());
        employeeRepository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return ChangeEmployeeTitleCommand.class;
    }

}
