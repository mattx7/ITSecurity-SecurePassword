package its.secur_pass.pass_validation;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

/**
 * Utility class for password validation.
 */
public class PasswordValidator {

    /**
     * Private cons. for utility classes.
     */
    private PasswordValidator() {
    }

    public static boolean isValid(@Nonnull final String password) {
        Preconditions.checkNotNull(password, "password must not be null.");

        final String atLeastOneDigit = "(?=.*[0-9])";
        final String adLeastOneUpperCase = "(?=.*[A-Z])";
        final String atLeastOneSmallCharacter = "(?=.*[a-z])";
        final String atLeastOneSpecialCharacter = "(?=.*[@#$%^&+=])";
        final String noWhitespaces = "(?=\\S+$)";
        final String atLeastEightCharacters = ".{8,}";

        String pattern = atLeastOneDigit +
                atLeastOneSmallCharacter +
                adLeastOneUpperCase +
                atLeastOneSpecialCharacter +
                noWhitespaces +
                atLeastEightCharacters;

        return password.matches(pattern);
    }

}
