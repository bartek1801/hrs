package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.users.RegisterUserHandler;
import pl.com.bottega.hrs.application.users.UpdateUserHandler;
import pl.com.bottega.hrs.application.users.UserDto;
import pl.com.bottega.hrs.application.users.UserFinder;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateUserTest extends AcceptanceTest {

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private RegisterUserHandler registerUserHandler;

    @Autowired
    private UpdateUserHandler updateUserHandler;

    @Test
    public void test(){

    }

    //@Test
    public void shouldUpdateUser(){
        //TODO nie działa razem z innymi testami!!!
        //given
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("login123");
        command.setPassword("testPassword");
        command.setRepeatedPassword("testPassword");
        registerUserHandler.handle(command);

        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setUserNo(1);
        updateCommand.setLogin("newLogin");
        updateCommand.setPassword("newPassword");
        updateCommand.setRepeatedPassword("newPassword");
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(Role.STANDARD);
        newRoles.add(Role.ADMIN);
        updateCommand.setRoles(newRoles);
        updateUserHandler.handle(updateCommand);

        //then
        UserDto userDto = userFinder.getUserDetails(1);
        assertEquals("newLogin", userDto.getLogin());
        assertEquals("newPassword", userDto.getPassword());
        //assertTrue(userFinder.getUserDetails(1).getRoles().contains(Role.STANDARD));
        //assertTrue(userFinder.getUserDetails(1).getRoles().contains(Role.ADMIN));
    }

}
