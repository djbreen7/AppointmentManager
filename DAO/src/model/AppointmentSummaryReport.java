package model;

/**
 * A report container for appointment summaries.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class AppointmentSummaryReport {
    private String month;
    private String type;
    private int total;

    
    /** 
     * @return String
     */
    public String getMonth() {
        return month;
    }

    
    /** 
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    
    /** 
     * @return String
     */
    public String getType() {
        return type;
    }

    
    /** 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /** 
     * @return int
     */
    public int getTotal() {
        return total;
    }

    
    /** 
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
