package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;


@Component
public class AddDepartmentHandler implements Handler<AddDepartmentCommand> {

    private DepartmentRepository departmentRepository;

    public AddDepartmentHandler(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void handle(AddDepartmentCommand command) {
        Department department = new Department(command.getNumber(), command.getName());
        departmentRepository.save(department);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddDepartmentCommand.class;
    }
}
