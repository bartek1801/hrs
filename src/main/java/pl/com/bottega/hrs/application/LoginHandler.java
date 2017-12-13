package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.CurrentUser;
import pl.com.bottega.hrs.application.users.LoginCommand;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.UserRepository;
import pl.com.bottega.hrs.model.commands.Command;

@Component
public class LoginHandler implements Handler<LoginCommand> {

    private UserRepository userRepository;

    private CurrentUser currentUser;

    public LoginHandler(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }


    @Override
    public void handle(LoginCommand command) {
        User user = userRepository.get(command.getLogin(), command.getPassword());
        currentUser.login(user);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return LoginCommand.class;
    }
}
