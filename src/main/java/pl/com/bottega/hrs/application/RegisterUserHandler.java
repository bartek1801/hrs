package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.RegisterUserCommand;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.UserRepository;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

import javax.transaction.Transactional;

@Component
@Transactional
public class RegisterUserHandler implements Handler<RegisterUserCommand> {

    private UserRepository userRepository;

    public RegisterUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(RegisterUserCommand command) {
        validateLoginFree(command);
        userRepository.save(new User(command.getLogin(), command.getPassword()));
    }

    private void validateLoginFree(RegisterUserCommand command) {
        if(userRepository.isLoginOccupied(command.getLogin())) {
            ValidationErrors errors = new ValidationErrors();
            errors.add("login", "is occupied");
            throw new CommandInvalidException(errors);
        }
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterUserCommand.class;
    }
}
