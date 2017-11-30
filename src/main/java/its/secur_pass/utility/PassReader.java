package its.secur_pass.utility;

import its.secur_pass.enitities.User;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PassReader {
    private static final Logger LOG = Logger.getLogger(PassReader.class);

    @Nonnull
    private final String filename;

    @Nonnull
    private static final String CVS_SPLIT = ",";

    public PassReader(@Nonnull final String filename) {
        this.filename = filename;
    }

    /**
     * Return user by given name. Case-sensitive.
     *
     * @param name Not null.
     * @return null if user can't be found.
     */
    @Nullable
    public User findByName(@Nonnull final String name) {
        String line = "";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(filename)))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] words = line.split(CVS_SPLIT);

                if (words.length != 2)
                    throw new IllegalArgumentException("line is not valid");

                if (name.equals(words[0]))
                    return new User(words[0], words[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
