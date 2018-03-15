package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.users.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterUserTest extends AcceptanceTest {

    @Autowired
    private CommandGateway gateway;

    @Test
    public void shouldRegisterUser() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setLogin("test");
        cmd.setPassword("123456");
        cmd.setRepeatedPassword("123456");

        gateway.execute(cmd);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowDuplicateLogin() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setLogin("test");
        cmd.setPassword("123456");
        cmd.setRepeatedPassword("123456");
        gateway.execute(cmd);

        gateway.execute(cmd);
    }

}
