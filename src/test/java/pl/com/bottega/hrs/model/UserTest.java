package pl.com.bottega.hrs.model;


import org.junit.Test;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private final User  sut = new User("andrzej2000", "qwerty123");


    @Test
    public void shouldUpdateAllElementsOfProfile(){
        //when
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(Role.STANDARD);
        newRoles.add(Role.ADMIN);
        sut.updateProfile("janusz1920", "januszek123", newRoles);

        //then
        assertEquals("janusz1920", sut.getLogin());
        assertEquals("januszek123", sut.getPassword());
        assertTrue(sut.getRoles().contains(Role.STANDARD));
        assertTrue(sut.getRoles().contains(Role.ADMIN));
    }

    @Test
    public void shouldUpdateOnlyLogin(){
        //when
        sut.updateProfile("grazyna123", null, null);

        //then
        assertEquals("grazyna123", sut.getLogin());
        assertEquals("qwerty123", sut.getPassword());
        assertTrue(sut.getRoles().contains(Role.STANDARD));
    }

    @Test
    public void shouldUpdateOnlyPassword(){
        //when
        sut.updateProfile(null, "newPassword", null);

        //then
        assertEquals("andrzej2000", sut.getLogin());
        assertEquals("newPassword", sut.getPassword());
        assertTrue(sut.getRoles().contains(Role.STANDARD));
    }

    @Test
    public void shoiuldUpdateOnlyRoles(){
        //when
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(Role.STANDARD);
        newRoles.add(Role.ADMIN);
        sut.updateProfile(null, null, newRoles);

        //then
        assertEquals("andrzej2000", sut.getLogin());
        assertEquals("qwerty123", sut.getPassword());
        assertTrue(sut.getRoles().contains(Role.STANDARD));
        assertTrue(sut.getRoles().contains(Role.ADMIN));

    }



}
