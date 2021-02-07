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
     * @return The instance of DataManager.
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
     * @return The customer id for the current scene.
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
     * @return The appointment id for the current scene.
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
     * @return A boolean indicating if the user has been notified of upcoming appointments.
     */
    public boolean getHasVisitedAppointments() {
        return hasVisitedAppointments;
    }

    
    /**
     * Prevents the user from seeing a notification for upcoming appointments upon login.
     *
     * @param hasVisitedAppointments Boolean - true to disable alerts.
     */
    public void setHasVisitedAppointments(boolean hasVisitedAppointments) {
        this.hasVisitedAppointments = hasVisitedAppointments;
    }
}
