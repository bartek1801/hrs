package pl.com.bottega.hrs.application;

public interface UserFinder {

    UserDto getUserDetails(Integer userNo);

    UserDto getUserDetails(String login);
}