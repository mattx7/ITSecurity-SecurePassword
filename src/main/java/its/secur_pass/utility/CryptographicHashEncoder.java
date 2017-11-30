package its.secur_pass.utility;

import javax.annotation.Nonnull;

public interface CryptographicHashEncoder {

    /**
     * Encrypt a string.
     *
     * @param string Not null.
     * @return Hashed string.
     */
    String encrypt(@Nonnull String string);

}
