package pl.com.bottega.hrs.model.commands;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrors {

    private Map<String, String> errors = new HashMap<>();

    public void add(String field, String error) {
        errors.put(field, error);
    }

    public boolean any() {
        return !errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return "Invalid request parameters";
    }
}
