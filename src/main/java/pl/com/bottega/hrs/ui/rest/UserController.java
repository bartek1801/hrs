package pl.com.bottega.hrs.ui.rest;


import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserFinder;

@RestController
public class UserController {

    private UserFinder userFinder;

    private CommandGateway commandGateway;

    public UserController(UserFinder userFinder, CommandGateway commandGateway) {
        this.userFinder = userFinder;
        this.commandGateway = commandGateway;
    }

    //TODO request tworzenie nowych użytkowników (nowy controller) request POST do tabeli users
    //przyjmuje JSONa "login" : "janek" i "password" : "dupa"
    //stworzyć RegisterUserCommand i RegisterUserHandler
    //Podczas tworzenia user theba sprawdzić w Handlerze czy użytkownik o takim loginie istnieje juz w repozytorium
    //UserRepository i JpaUserRepository
    //UserController do requestów tworzenia nowych userów

    public void register(){

    }



    //TODO zmiana hasła request PATCH /users/{login} w JSOnie przychodzi nowe hasło "password"




}
