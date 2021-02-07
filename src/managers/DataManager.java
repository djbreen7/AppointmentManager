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
    private boolean hasVisitedAppointments;

    
    /** 
     * @return DataManager
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    
    /** 
     * @return int
     */
    public int getAndClearCustomerId() {
        var result = customerId;
        this.customerId = 0;
        return result;
    }

    
    /** 
     * @param id
     */
    public void setCustomerId(int id) {
        this.customerId = id;
    }

    
    /** 
     * @return int
     */
    public int getAndClearAppointmentId() {
        var result = appointmentId;
        this.appointmentId = 0;
        return result;
    }

    
    /** 
     * @param id
     */
    public void setAppointmentId(int id) {
        this.appointmentId = id;
    }

    
    /** 
     * @return boolean
     */
    public boolean getHasVisitedAppointments() {
        return hasVisitedAppointments;
    }

    
    /** 
     * @param hasVisitedAppointments
     */
    public void setHasVisitedAppointments(boolean hasVisitedAppointments) {
        this.hasVisitedAppointments = hasVisitedAppointments;
    }
}
