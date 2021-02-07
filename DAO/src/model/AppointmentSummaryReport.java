package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class AppointmentSummaryReport {
    private String month;
    private String type;
    private int total;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
