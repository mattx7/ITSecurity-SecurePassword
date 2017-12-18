package its.secur_pass.utility;

import com.google.common.base.Preconditions;
import its.secur_pass.enitities.User;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class PassDAO {
    private static final Logger LOG = Logger.getLogger(PassDAO.class);


    @Nonnull
    private CryptographicHashEncoder hashEncoder;

    @Nonnull
    private static final String CVS_SPLIT = ",";

    @Nonnull
    private URL resource;

    public PassDAO(@Nonnull final String filename) {
        hashEncoder = SecureHashAlgorithm.SHA_256;
        this.resource = getResource(filename);
        if (resource == null)
            throw new IllegalArgumentException("File does not exist");

    }

    /**
     * Saves a user into the file.
     *
     * @param user Not null.
     */
    public void registerNewUser(@Nonnull final User user) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(resource.toURI()))))) {
            final SecureHashResult secureHashResult = hashEncoder.encrypt(user.getPassword());
            out.write(user.getName());
            out.write(CVS_SPLIT);
            out.write(secureHashResult.getSaltStr());
            out.write(CVS_SPLIT);
            out.write(secureHashResult.getHashStr());
            out.flush();
            out.close();
            LOG.info("Saves user " + user.getName() + " with: " + secureHashResult.getSaltStr() + ", " + secureHashResult.getHashStr());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if user is already registered. Case-sensitive.
     *
     * @param user Not null.
     * @return True if user is already registered.
     */
    public boolean isRegisteredUser(@Nonnull final User user) {
        String line = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(resource.openStream()))) {

            while ((line = in.readLine()) != null) {
                // use comma as separator
                String[] words = createUser(line);

                boolean isValidName = user.getName().equals(words[0]);
                if (isValidName) {
                    SecureHashResult encryptPass = hashEncoder.encrypt(words[1], user.getPassword());
                    final String salt = encryptPass.getSaltStr();
                    final String password = encryptPass.getHashStr();

                    LOG.debug("Used Salt: " + salt);
                    LOG.debug("PassHash: " + password);

                    boolean isValidPassword = password.equalsIgnoreCase(words[2]);
                    if (isValidPassword)
                        return true;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String[] createUser(final String line) {
        String[] words = line.split(CVS_SPLIT);

        Preconditions.checkState(words.length == 3);
        return words;
    }

    private URL getResource(final @Nonnull String filename) {
        return getClass().getClassLoader().getResource(filename);
    }
}
