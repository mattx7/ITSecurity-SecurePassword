package its.secur_pass.terminal;

import its.secur_pass.data_access.PassDAO;
import its.secur_pass.enitities.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.Console;

public class CommandPrompt {
    public static final Logger LOG = Logger.getLogger(CommandPrompt.class);

    private static final String AWAIT_COMMAND_MARKER = "#IN> ";

    @Nonnull
    private final Console terminal;

    @Nonnull
    private final PassDAO passDAO;

    /**
     * Constructor with given DAO.
     *
     * @param passDAO Not null.
     */
    public CommandPrompt(@Nonnull final PassDAO passDAO) {
        this.terminal = System.console();
        this.passDAO = passDAO;
    }

    /**
     * Runs the command prompt in the terminal.
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // interactions
            showHelp();

            if (await(AWAIT_COMMAND_MARKER).equalsIgnoreCase(Commands.LOGIN))
                handleLogin();
            else if (await(AWAIT_COMMAND_MARKER).equalsIgnoreCase(Commands.REGISTER))
                handleRegistration();
            else
                showHelp();
        }
    }

    // =========== Uses Cases ============

    private void handleRegistration() {
        print("====== REGISTRATION ======");
        User user = new User(
                await("Username: "),
                await("Password: "));
        passDAO.registerNewUser(user);
    }

    private void handleLogin() {
        print("====== LOGIN ======");
        User user = new User(
                await("Username: "),
                await("Password: "));
        boolean isRegistered = passDAO.isRegisteredUser(user);

        print("Result: " + (isRegistered ? "User is logged in!" : "Username or Password is wrong!"));
    }

    private void showHelp() {
        print("Commands: \n" + Commands.LOGIN + " \n" + Commands.REGISTER);
    }

    // ========== utility ==========

    /**
     * Awaits an input in the terminal.
     *
     * @param msg Shows this message in the terminal to describe the requested input.
     * @return The given input. Not null.
     */
    @Nonnull
    private String await(@Nonnull String msg) {
        return StringUtils.defaultString(terminal.readLine(msg));
    }

    /**
     * {@link System#out#print(String)}
     */
    private void print(@Nonnull final String msg) {
        System.out.println(msg);
    }
}
