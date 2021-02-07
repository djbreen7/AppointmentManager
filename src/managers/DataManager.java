package managers;

/**
 * A Singleton class for managing data between scenes.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class DataManager {
    private static DataManager instance = null;
    private int customerId;
    private int appointmentId;
    private boolean hasVisitedAppointments;

    
    /**
     * Creates a DataManager instance if once doesn't exist, then returns the instance.
     *
     * @return DataManager
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    
    /**
     * A helper for retrieving a customer id from another scene. The ID is cleared after retrieval.
     *
     * @return int
     */
    public int getAndClearCustomerId() {
        var result = customerId;
        this.customerId = 0;
        return result;
    }

    
    /**
     * A helper for passing a customer id to another scene.
     *
     * @param id The ID of the customer to store.
     */
    public void setCustomerId(int id) {
        this.customerId = id;
    }

    
    /**
     * A helper for retrieving an appointment id from another scene. The ID is cleared after retrieval.
     *
     * @return int
     */
    public int getAndClearAppointmentId() {
        var result = appointmentId;
        this.appointmentId = 0;
        return result;
    }

    
    /**
     * A helper for passing an appointment id to another scene.
     *
     * @param id The ID of the appointment to store.
     */
    public void setAppointmentId(int id) {
        this.appointmentId = id;
    }

    
    /**
     * Checks to see if the user has already been notified of upcoming appointments upon login.
     *
     * @return boolean
     */
    public boolean getHasVisitedAppointments() {
        return hasVisitedAppointments;
    }

    
    /**
     * Prevents the user from seeing a notification for upcoming appointments upon login.
     *
     * @param hasVisitedAppointments
     */
    public void setHasVisitedAppointments(boolean hasVisitedAppointments) {
        this.hasVisitedAppointments = hasVisitedAppointments;
    }
}
