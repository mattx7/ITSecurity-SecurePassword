package its.secur_pass.utility;

import javax.annotation.Nonnull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public enum SecureHashAlgorithm implements CryptographicHashEncoder {
    SHA_1("SHA-1"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512");

    private final String version;

    SecureHashAlgorithm(final String version) {
        this.version = version;
    }

    public String encrypt(@Nonnull String string) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(version);
            md.update(getSalt());
            byte[] bytes = md.digest(string.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); // TODO maybe?
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
