package its.secur_pass.data_access;

import com.google.common.base.Preconditions;
import its.secur_pass.enitities.User;
import its.secur_pass.hash_algorithm.CryptographicHashEncoder;
import its.secur_pass.hash_algorithm.SecureHashAlgorithm;
import its.secur_pass.hash_algorithm.SecureHashResult;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PassDAO {
    private static final Logger LOG = Logger.getLogger(PassDAO.class);

    @Nonnull
    private CryptographicHashEncoder hashEncoder;

    @Nonnull
    private static final String CVS_SPLIT = ",";

    @Nonnull
    private File file;

    public PassDAO(@Nonnull final String filename) {
        hashEncoder = SecureHashAlgorithm.SHA_256;

        // already existing text file under resources
        URL temp = getClass().getClassLoader().getResource(filename);

        // file under root directory
        if (temp == null) {
            try {
                final Path path = Paths.get(filename);
                if (!path.toFile().exists())
                    Files.createFile(path);
                file = path.toFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            file = Paths.get(temp.getPath()).toFile();
        }
    }

    /**
     * Saves a user into the file.
     *
     * @param user Not null.
     */
    public void registerNewUser(@Nonnull final User user) {
        try (BufferedWriter out =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {

            final SecureHashResult secureHashResult = hashEncoder.encrypt(user.getPassword());

            out.write(user.getName());
            out.write(CVS_SPLIT);
            out.write(secureHashResult.getSaltStr());
            out.write(CVS_SPLIT);
            out.write(secureHashResult.getHashStr());
            out.flush();
            out.close();

            LOG.info("Saves user " + user.getName() + " with: " + secureHashResult.getSaltStr() + ", " + secureHashResult.getHashStr());
        } catch (IOException e) {
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

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

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
            LOG.error("", e);
        }

        return false;
    }

    private String[] createUser(final String line) {
        String[] words = line.split(CVS_SPLIT);
        Preconditions.checkState(words.length == 3);
        return words;
    }
}
