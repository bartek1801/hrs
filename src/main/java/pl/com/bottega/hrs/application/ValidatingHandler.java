package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

public class ValidatingHandler<C extends Command> implements Handler<C> {

    private Handler<C> decorated;

    public ValidatingHandler(Handler<C> decorated) {
        this.decorated = decorated;
    }

    @Override
    public void handle(C command) {
        validate(command);
        decorated.handle(command);
    }

    private void validate(Command command) {
        ValidationErrors validationErrors = new ValidationErrors();
        command.validate(validationErrors);
        if(validationErrors.any())
            throw new CommandInvalidException(validationErrors);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return decorated.getSupportedCommandClass();
    }
}
