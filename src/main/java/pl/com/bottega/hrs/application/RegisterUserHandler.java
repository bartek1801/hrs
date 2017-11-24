package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.Command;

public class RegisterUserHandler implements Handler<RegisterUserCommand> {




    @Override
    public void handle(RegisterUserCommand command) {

    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return null;
    }
}
