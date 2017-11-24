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

}
