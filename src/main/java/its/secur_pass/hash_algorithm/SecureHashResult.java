package its.secur_pass.hash_algorithm;

import its.secur_pass.utility.ByteHexConverter;

import javax.annotation.Nonnull;

public class SecureHashResult {

    @Nonnull
    private byte[] salt;

    @Nonnull
    private byte[] hash;

    public SecureHashResult(@Nonnull final byte[] salt,
                            @Nonnull final byte[] hash) {
        this.salt = salt;
        this.hash = hash;
    }

    @Nonnull
    public byte[] getSalt() {
        return salt;
    }

    @Nonnull
    public byte[] getHash() {
        return hash;
    }

    @Nonnull
    public String getSaltStr() {
        return ByteHexConverter.convert(salt);
    }

    @Nonnull
    public String getHashStr() {
        return ByteHexConverter.convert(hash);
    }

}
