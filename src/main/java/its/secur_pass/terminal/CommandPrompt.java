package its.secur_pass.terminal;

import its.secur_pass.data_access.PassDAO;
import its.secur_pass.entities.User;
import its.secur_pass.pass_validation.PasswordValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Console;
import java.util.Arrays;

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

    /**
     * LOGIN
     */
    private void handleLogin() {
        terminal.flush();
        print("====== LOGIN ======");
        User user = new User(
                await("Username: "),
                awaitPassword());
        boolean isRegistered = passDAO.isRegisteredUser(user);

        print("Result: " + (isRegistered ? "User is logged in!" : "Username or Password is wrong!"));
    }

    /**
     * REGISTRATION
     */
    private void handleRegistration() {
        terminal.flush();
        print("====== REGISTRATION ======");
        final String name = await("Username: ");
        final String password = awaitValidPassword(3);

        if (password == null)
            return;

        User user = new User(name, password);
        passDAO.registerNewUser(user);
    }


    // ========== utility ==========

    /**
     * @param possibleTries Chances to insert a valid password.
     * @return Null, if all chances are missed.
     */
    @Nullable
    private String awaitValidPassword(int possibleTries) {
        final String password = awaitPassword();

        if (possibleTries == 0) {
            print("Sorry try it later again!");
            return null;
        }

        if (PasswordValidator.isValid(password))
            return password;

        print("Sorry your password is too weak try again");
        return awaitValidPassword(--possibleTries);
    }

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
     * Awaits an password as input. Will no show the input.
     */
    @Nonnull
    private String awaitPassword() {
        return StringUtils.defaultString(Arrays.toString(terminal.readPassword("Password: ")));
    }

    private void showHelp() {
        print("Commands: \n" + Commands.LOGIN + " \n" + Commands.REGISTER);
    }

    /**
     * {@link System#out#print(String)}
     */
    private void print(@Nonnull final String msg) {
        System.out.println(msg);
    }
}
