package pl.com.bottega.hrs.ui.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.users.CurrentUser;
import pl.com.bottega.hrs.application.users.LoginCommand;
import pl.com.bottega.hrs.application.users.UserDto;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;

import java.util.Optional;

@RestController("/users")
public class UserController {

    private CommandGateway commandGateway;

    private CurrentUser currentUser;

    public UserController(CommandGateway commandGateway, CurrentUser currentUser) {
        this.commandGateway = commandGateway;
        this.currentUser = currentUser;
    }

    @PostMapping
    public void register(@RequestBody RegisterUserCommand command) {
        commandGateway.execute(command);
    }

    @PutMapping("{userNo}")
    public void updateUser(@PathVariable Integer userNo, @RequestBody UpdateUserCommand command) {
        command.setUserNo(userNo);
        commandGateway.execute(command);
    }

    @PutMapping("/session")
    public void login(@PathVariable LoginCommand loginCommand){
        commandGateway.execute(loginCommand);
    }

    @DeleteMapping("/logout")
    public void logout(){
        currentUser.logout();
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrent(){
        Optional<UserDto> userDtoOptional = currentUser.getUserInfo();
        if (userDtoOptional.isPresent())
            return new ResponseEntity<>(userDtoOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
