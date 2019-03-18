package nl.tudelft.gogreen.server.tests;

import nl.tudelft.gogreen.server.models.activity.config.InputType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputTypeTest {
    @Test
    public void shouldReturnTrueValidFloat() {
        assertTrue(InputType.FLOAT.getValidator().isValid("12.4"));
        assertTrue(InputType.FLOAT.getValidator().isValid("1.0"));
        assertTrue(InputType.FLOAT.getValidator().isValid("0.12"));
    }

    @Test
    public void shouldReturnFalseInvalidFloat() {
        assertFalse(InputType.FLOAT.getValidator().isValid("awd"));
        assertFalse(InputType.FLOAT.getValidator().isValid(".213."));
        assertFalse(InputType.FLOAT.getValidator().isValid(null));
    }

    @Test
    public void shouldReturnTrueValidBoolean() {
        assertTrue(InputType.BOOLEAN.getValidator().isValid("true"));
        assertTrue(InputType.BOOLEAN.getValidator().isValid("false"));
    }

    @Test
    public void shouldReturnFalseInvalidBoolean() {
        assertFalse(InputType.BOOLEAN.getValidator().isValid("awd"));
        assertFalse(InputType.BOOLEAN.getValidator().isValid(".    1"));
        assertFalse(InputType.BOOLEAN.getValidator().isValid(null));
    }

    @Test
    public void shouldReturnTrueValidString() {
        assertTrue(InputType.SELECT.getValidator().isValid("awdawd"));
        assertTrue(InputType.SELECT.getValidator().isValid(""));
    }

    @Test
    public void shouldReturnFalseInvalidString() {
        assertFalse(InputType.SELECT.getValidator().isValid(null));
    }
}
