package pl.com.bottega.hrs.infrastructure;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.CurrentUser;
import pl.com.bottega.hrs.application.users.SecurityException;

@Component
@Aspect
public class SecurityAspect {

    private CurrentUser currentUser;

    public SecurityAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Before("@within(secured)")
    public void checkSecurity(Secured secured) {
        if(!currentUser.isAuthenticated(secured.roles()))
            throw new SecurityException();
    }

    @Before("@annotation(secured)")
    public void checkMethodSecurity(Secured secured) {
        checkSecurity(secured);
    }

}
