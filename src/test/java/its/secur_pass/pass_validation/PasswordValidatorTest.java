package its.secur_pass.pass_validation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordValidatorTest {

    @Test
    public void testValidation() throws Exception {
        boolean valid = PasswordValidator.isValid("abcABC123#");
        assertTrue(valid);
    }

    @DataProvider(name = "testValidationNegativeData")
    private Object[][] testValidationNegativeData(){
        return new Object[][] {
                {"abcAB1#"}, // min 8 character
                {"ABC123###"},
                {"abc123###"},
                {"abcABC###"},
        };
    }

    @Test(dataProvider = "testValidationNegativeData")
    public void testValidationNegative(final String password) throws Exception {
        boolean valid = PasswordValidator.isValid(password);
        assertFalse(valid);
    }

}