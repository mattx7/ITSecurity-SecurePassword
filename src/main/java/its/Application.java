package its;

import its.secur_pass.enitities.User;
import its.secur_pass.utility.PassDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.Console;
import java.net.URISyntaxException;

/**
 * Runs application and interactions.
 */
public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class);
    private static final Console terminal = System.console();
    private static final String AWAIT_COMMAND_MARKER = "#IN>";
    public static final PassDAO PASS_DAO = new PassDAO("passes.csv");
    public static final String LOGIN = "!login";
    public static final String REGISTRATION = "!registration";

    /**
     * Holds only the main method an instance is not necessary.
     */
    private Application() {
    }

    public static void main(String[] args) throws URISyntaxException {
        LOG.debug("Starting application...");

        while (!Thread.currentThread().isInterrupted()) {
            // interactions
            showHelp();

            if (await(AWAIT_COMMAND_MARKER).equalsIgnoreCase(LOGIN))
                handleLogin();
            else if (await(AWAIT_COMMAND_MARKER).equalsIgnoreCase(REGISTRATION))
                handleRegistration();
            else
                showHelp();
        }

    }

    private static void handleRegistration() {
        print("====== REGISTRATION ======");
        User user = new User(
                await("Username: "),
                await("Password: "));
    }

    private static void showHelp() {
        print("Commands: \n" +
                LOGIN + " \n" +
                REGISTRATION);
    }

    private static void handleLogin() {
        print("====== LOGIN ======");
        User user = new User(await("Username: "), await("Password: "));
        boolean isRegistered = PASS_DAO.isRegisteredUser(user);

        print("Result: " + (isRegistered ? "User is logged in!" : "User not found!"));
    }

    @Nonnull
    private static String await(@Nonnull String msg) {
        return StringUtils.defaultString(terminal.readLine(msg));
    }

    private static void print(@Nonnull final String msg) {
        System.out.println(msg);
    }


}