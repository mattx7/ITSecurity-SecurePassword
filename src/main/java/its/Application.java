package its;

import its.secur_pass.enitities.User;
import its.secur_pass.utility.PassReader;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.Console;
import java.net.URISyntaxException;

/**
 * Runs application and interactions.
 */
public class Application {
    private static Logger LOG = Logger.getLogger(Application.class);
    private static Console terminal = System.console();

    /**
     * Holds only the main method an instance is not necessary.
     */
    private Application() {
    }

    public static void main(String[] args) throws URISyntaxException {
        LOG.debug("Starting application...");

        while (!Thread.currentThread().isInterrupted()) {
            // interactions
            print("====== LOGIN ======");
            User user = new User(await("Username: "), await("Password: "));
            PassReader passReader = new PassReader("passes.csv");
            boolean isRegistered = passReader.isRegisteredUser(user);

            print("Result: " + (isRegistered ? "User is logged in!" : "User not found!"));
        }

    }

    @Nonnull
    private static String await(@Nonnull String msg) {
        return org.apache.commons.lang3.StringUtils.defaultString(terminal.readLine(msg));
    }

    private static void print(@Nonnull final String msg) {
        System.out.println(msg);
    }


}