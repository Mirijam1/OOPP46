package nl.tudelft.gogreen.server.models.activity.config;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.validator.routines.FloatValidator;

import java.util.Objects;

public enum InputType {
    FLOAT(input -> {
        return FloatValidator.getInstance().isValid(input);
    }),
    BOOLEAN(input -> BooleanUtils.toBooleanObject(input) != null),
    SELECT(Objects::nonNull);

    public interface InputValidator {
        boolean isValid(String input);
    }

    private InputValidator validator;

    InputType(InputValidator validator) {
        this.validator = validator;
    }

    public InputValidator getValidator() {
        return validator;
    }
}