package its.secur_pass.hash_algorithm;

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

    @Nonnull
    public SecureHashResult encrypt(@Nonnull String string) {
        try {
            return encrypt(getSalt(), string);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SecureHashResult encrypt(@Nonnull final String salt,
                                    @Nonnull final String string) {
        return encrypt(ByteHexConverter.convert(salt), string);
    }

    @Override
    public SecureHashResult encrypt(@Nonnull final byte[] salt,
                                    @Nonnull final String string) {
        try {
            MessageDigest sha = MessageDigest.getInstance(version);
            sha.update(salt);

            return new SecureHashResult(salt, sha.digest(string.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
