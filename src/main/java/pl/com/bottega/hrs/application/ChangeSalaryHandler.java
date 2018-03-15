package pl.com.bottega.hrs.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.ChangeSalaryCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.events.SalaryChangedEvent;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class ChangeSalaryHandler implements Handler<ChangeSalaryCommand> {

    private EmployeeRepository repository;
    private ApplicationEventPublisher eventPublisher;

    public ChangeSalaryHandler(EmployeeRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void handle(ChangeSalaryCommand cmd) {
        Employee employee = repository.get(cmd.getEmpNo());
        employee.changeSalary(cmd.getAmount());
        repository.save(employee);
        eventPublisher.publishEvent(new SalaryChangedEvent(cmd.getEmpNo(), cmd.getAmount()));
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return ChangeSalaryCommand.class;
    }
}
