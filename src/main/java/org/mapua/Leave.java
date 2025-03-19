package org.mapua;

public class Leave {
    private String employeeId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private String status; // "Pending", "Approved", or "Declined"

    // Existing getters and setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    // New status getter and setter
    public String getStatus() { return status == null ? "Pending" : status; }
    public void setStatus(String status) { this.status = status; }
}