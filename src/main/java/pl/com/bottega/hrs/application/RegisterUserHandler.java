package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.model.repositories.UserRepository;

@Component
public class RegisterUserHandler implements Handler<RegisterUserCommand> {

    private UserRepository userRepository;

    public RegisterUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(RegisterUserCommand command) {
        if (!userRepository.checkLoginAvailability(command.getLogin())) {
            ValidationErrors validationErrors = new ValidationErrors();
            validationErrors.add("login", "The login is busy, please try with another login");
            throw new CommandInvalidException(validationErrors);
        }
        User user = new User(command.getLogin(), command.getPassword());
        userRepository.save(user);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterUserCommand.class;
    }
}
