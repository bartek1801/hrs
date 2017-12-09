package pl.com.bottega.hrs.application.users;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.application.Handler;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.application.users.UserRepository;

@Component
public class UpdateUserHandler implements Handler<UpdateUserCommand> {

    private UserRepository userRepository;

    public UpdateUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(UpdateUserCommand command) {
        if (command.getLogin() != null &&  !userRepository.checkLoginAvailability(command.getLogin())) {
            ValidationErrors validationErrors = new ValidationErrors();
            validationErrors.add("login", "The login is busy, please try with another login");
            throw new CommandInvalidException(validationErrors);
        }
        User user = userRepository.getUser(command.getUserNo());
        user.updateProfile(command.getLogin(), command.getPassword(), command.getRoles());
        userRepository.save(user);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return UpdateUserCommand.class;
    }
}
