package logger;

import java.util.StringJoiner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A wrapper for java.util.Logging to simplify logging to login_activity.txt.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class ActivityLogger {
    private final Logger logger = Logger.getLogger("ActivityLog");

    private FileHandler fh;
    private StringJoiner sj;

    public ActivityLogger() {
        try {
            sj = new StringJoiner("/");
            sj.add(System.getProperty("user.dir"));
            sj.add("login_activity.txt");

            fh = new FileHandler(sj.toString(), true);
            fh.setFormatter(new SimpleFormatter());

            logger.setUseParentHandlers(false);
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a message related to a user's activity such as log in attempts.
     *
     * @param userName The name of the user performing the activity.
     * @param message  The message containing information about the activity.
     */
    public void info(String userName, String message) {
        var sj = new StringJoiner(" ");
        sj.add(userName);
        sj.add(message);

        logger.info(sj.toString());
    }
}
