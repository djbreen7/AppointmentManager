package logger;

import java.util.StringJoiner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ActivityLogger {
    private final Logger logger = Logger.getLogger("ActivityLog");

    private FileHandler fh;
    private StringJoiner filePath;

    public ActivityLogger() {
        try {
            filePath = new StringJoiner("/");
            filePath.add(System.getProperty("user.dir"));
            filePath.add("login_activity.txt");

            fh = new FileHandler(filePath.toString());
            fh.setFormatter(new SimpleFormatter());

            logger.setUseParentHandlers(false);
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(String userName, String message) {
        var log = new StringJoiner(" ");
        log.add(userName);
        log.add(message);

        logger.info(log.toString());
    }
}
