package pl.com.bottega.hrs.model.commands;

public interface Command {

    default void validate(ValidationErrors validationErrors) {

    }

    default void validatePresence(ValidationErrors errors, String field, String value) {
        if (value == null || value.trim().length() == 0) {
            errors.add(field, "can't be blank");
        }
    }

    default void validatePresence(ValidationErrors errors, String field, Object value) {
        if (value == null) {
            errors.add(field, "can't be blank");
        }
    }

    default void validateLogin(ValidationErrors errors, String login) {
        if(!validateMarks(login))
            errors.add("login", "login should contains only letters and numbers");
    }

    default boolean validateMarks(String login){
        String acceptableMarks = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Integer checkSum = 0;
        for (int i = 0; i < login.length(); i++){
            for (int k = 0; k < acceptableMarks.length();k++){
                if (login.charAt(i) == acceptableMarks.charAt(k) ) {
                    checkSum += 1;
                    continue;
                }
            }
        }
        return checkSum == login.length();
    }


    default void validateRepeatedPassword(ValidationErrors errors, String password, String repeatedPassword) {
        if (repeatedPassword != null && !password.equals(repeatedPassword))
            errors.add("password", "password and repeated password should be the same");
    }

    default void validatePasswordLength(ValidationErrors errors, String password) {
        if (password != null && password.length() < 6)
            errors.add("password", "password should contain at least 6 characters");
    }

}
