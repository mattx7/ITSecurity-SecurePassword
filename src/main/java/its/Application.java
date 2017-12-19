package its;

import its.secur_pass.data_access.PassDAO;
import its.secur_pass.terminal.CommandPrompt;
import org.apache.log4j.Logger;

/**
 * Runs application and interactions.
 */
public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class);

    /**
     * Holds only the main method an instance is not necessary.
     */
    private Application() {
    }

    public static void main(String[] args) {
        LOG.debug("Starting application...");

        new CommandPrompt(new PassDAO("registeredUser.csv")).run();
    }

}