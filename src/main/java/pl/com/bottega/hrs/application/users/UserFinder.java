package pl.com.bottega.hrs.application.users;

public interface UserFinder {

    UserDto getUserDetails(Integer userNo);

    UserDto getUserDetails(String login);
}
