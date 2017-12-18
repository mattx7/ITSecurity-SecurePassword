package its.secur_pass.hash_algorithm;

import javax.annotation.Nonnull;

public interface CryptographicHashEncoder {

    /**
     * Encrypt a string.
     *
     * @param string Not null.
     * @return Hashed string with the used salt.
     */
    SecureHashResult encrypt(@Nonnull String string);

    SecureHashResult encrypt(@Nonnull String salt, @Nonnull String string);

    SecureHashResult encrypt(@Nonnull byte[] salt, @Nonnull String string);

}
