package managers;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class DataManager {
    private static DataManager instance = null;
    private int customerId;
    private int appointmentId;

    private DataManager() {
        customerId = -1;
        appointmentId = -1;
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int getAndClearCustomerId() {
        var result = customerId;
        this.customerId = -1;
        return result;
    }

    public void setCustomerId(int id) {
        this.customerId = id;
    }

    public int getAndClearAppointmentId() {
        var result = appointmentId;
        this.appointmentId = -1;
        return result;
    }

    public void setAppointmentId(int id) {
        this.appointmentId = id;
    }
}
