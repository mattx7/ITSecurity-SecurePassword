package its.secur_pass;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Console;

/**
 * Runs application and interactions.
 */
public class Application {
    private static Logger LOG = Logger.getLogger(Application.class);

    /**
     * Holds only the main method an instance is not necessary.
     */
    private Application() {
    }

    public static void main(String[] args) {
        LOG.debug("Starting application...");

        // interactions
        Console terminal = System.console();
        print("Login or Register: ");
        terminal.readLine("User: ");
        terminal.readLine("Password: ");

    }

    private static void print(@NotNull String message) {
        System.out.println(message);
    }


}