package pl.com.bottega.hrs.ui.rest;


import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserDto;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.model.commands.ChangePasswordCommand;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;

@RestController
public class UserController {

    private UserFinder userFinder;

    private CommandGateway commandGateway;

    public UserController(UserFinder userFinder, CommandGateway commandGateway) {
        this.userFinder = userFinder;
        this.commandGateway = commandGateway;
    }

    @PostMapping("/users")
    public void register(@RequestBody RegisterUserCommand command){
        commandGateway.execute(command);
    }


    @PatchMapping("users/{login}")
    public void changePassword(@PathVariable String login, @RequestBody ChangePasswordCommand command){
        command.setLogin(login);
        commandGateway.execute(command);
    }

    //TODO zmiana hasła request PATCH /users/{login} w JSOnie przychodzi nowe hasło "password"




}
