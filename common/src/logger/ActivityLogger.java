package logger;

import java.util.StringJoiner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ActivityLogger {
    private final Logger logger = Logger.getLogger("ActivityLog");

    private FileHandler fh;
    private StringJoiner sj;

    public ActivityLogger() {
        try {
            sj = new StringJoiner("/");
            sj.add(System.getProperty("user.dir"));
            sj.add("login_activity.txt");

            fh = new FileHandler(sj.toString());
            fh.setFormatter(new SimpleFormatter());

            logger.setUseParentHandlers(false);
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(String userName, String message) {
        var sj = new StringJoiner(" ");
        sj.add(userName);
        sj.add(message);

        logger.info(sj.toString());
    }
}
