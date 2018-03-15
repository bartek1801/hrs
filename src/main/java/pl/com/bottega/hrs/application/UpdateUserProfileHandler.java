package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.UpdateUserProfileCommand;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.UserRepository;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

import javax.transaction.Transactional;

@Component
@Transactional
public class UpdateUserProfileHandler implements Handler<UpdateUserProfileCommand> {

    private UserRepository userRepository;

    public UpdateUserProfileHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(UpdateUserProfileCommand command) {
        User user = userRepository.get(command.getUserId());
        validateLoginFree(user, command);
        user.updateProfile(command);
        userRepository.save(user);
    }

    private void validateLoginFree(User user, UpdateUserProfileCommand command) {
        if(wantsChangeLogin(user, command) && userRepository.isLoginOccupied(command.getLogin())) {
            ValidationErrors errors = new ValidationErrors();
            errors.add("login", "is occupied");
            throw new CommandInvalidException(errors);
        }
    }

    private boolean wantsChangeLogin(User user, UpdateUserProfileCommand command) {
       return command.getLogin() != null && !command.getLogin().equals(user.getLogin());
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return UpdateUserProfileCommand.class;
    }
}
