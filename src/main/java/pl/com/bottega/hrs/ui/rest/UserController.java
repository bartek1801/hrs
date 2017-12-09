package pl.com.bottega.hrs.ui.rest;


import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.users.UserDto;
import pl.com.bottega.hrs.application.users.UserFinder;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;
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
    public void register(@RequestBody RegisterUserCommand command) {
        commandGateway.execute(command);
    }

    @GetMapping("/users/{userNo}")
    public UserDto get(@PathVariable Integer userNo){
        return userFinder.getUserDetails(userNo);
    }


    @PatchMapping("users/{userNo}")
    public UserDto updateUser(@PathVariable Integer userNo, @RequestBody UpdateUserCommand command) {
        command.setUserNo(userNo);
        commandGateway.execute(command);
        return userFinder.getUserDetails(userNo);
    }


}
