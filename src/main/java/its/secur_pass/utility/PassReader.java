package its.secur_pass.utility;

import its.secur_pass.enitities.User;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PassReader {
    private static final Logger LOG = Logger.getLogger(PassReader.class);

    @Nonnull
    private final String filename;

    @Nonnull
    private static final String CVS_SPLIT = ",";
    private CryptographicHashEncoder hashEncoder;

    public PassReader(@Nonnull final String filename) {
        this.filename = filename;
        hashEncoder = SecureHashAlgorithm.SHA_512;
    }

    /**
     * Return user by given name. Case-sensitive.
     *
     * @param user Not null.
     * @return
     */
    public boolean isRegisteredUser(@Nonnull final User user) {
        String line = "";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(filename)))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] words = line.split(CVS_SPLIT);

                if (words.length != 2)
                    throw new IllegalArgumentException("line is not valid");


                boolean isValidName = user.getName().equals(words[0]);
                String encryptPass = encrypt(user.getPassword());
                LOG.debug("PassHash: " + encryptPass);
                boolean isValidPassword = encryptPass.equals(words[1]);

                if (isValidName && isValidPassword)
                    return true;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String encrypt(String password) {
        return hashEncoder.encrypt(password);
    }
}
