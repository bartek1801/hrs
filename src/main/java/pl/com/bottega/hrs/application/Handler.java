package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.commands.Command;

public interface Handler<C extends Command> {

    void handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }

}
