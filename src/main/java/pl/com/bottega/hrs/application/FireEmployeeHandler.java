package pl.com.bottega.hrs.application;

import com.sun.corba.se.impl.activation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class FireEmployeeHandler implements Handler<FireEmployeeCommand> {

    private EmployeeRepository employeeRepository;

    public FireEmployeeHandler(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(FireEmployeeCommand cmd) {
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.fire();
        employeeRepository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return FireEmployeeCommand.class;
    }

}