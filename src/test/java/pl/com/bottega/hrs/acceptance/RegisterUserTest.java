package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.RegisterUserHandler;
import pl.com.bottega.hrs.application.UserDto;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterUserTest extends AcceptanceTest {

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private RegisterUserHandler registerUserHandler;

    @Test
    public void shouldRegisterNewUser(){
        //when
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("login123");
        command.setPassword("testPassword");
        command.setRepeatedPassword("testPassword");
        registerUserHandler.handle(command);

        //then
        UserDto userDto = userFinder.getUserDetails("login123");
        assertEquals("login123", userDto.getLogin());
        assertEquals("testPassword", userDto.getPassword());

    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotRegisterUsersWithTheSameLogins(){
        //given
        RegisterUserCommand command1 = new RegisterUserCommand();
        command1.setLogin("login");
        command1.setPassword("testPassword");
        command1.setRepeatedPassword("testPassword");
        registerUserHandler.handle(command1);

        //when
        RegisterUserCommand command2 = new RegisterUserCommand();
        command2.setLogin("login");
        command2.setPassword("testpass");
        command2.setRepeatedPassword("testpass");
        registerUserHandler.handle(command2);

    }



}
