package lk.ticket.model.systemControl;

public class SystemControlModule {
    private String systemStatus;
    private String configurationStatus;
    private int systemStoppedReleasedTicketCount;
    private int systemStoppedPoolSize;

    public SystemControlModule() {}

    public String toString(){
        return "SystemControlModule[System Status=" + systemStatus + ", Configuration Status=" + configurationStatus + ", System Stopped Released Ticket Count=" + systemStoppedReleasedTicketCount + ", System Stopped Purchased Ticket Count=" + systemStoppedPoolSize + "]";
    }

    //Getters & Setters
    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getConfigurationStatus() {
        return configurationStatus;
    }

    public void setConfigurationStatus(String configurationStatus) {
        this.configurationStatus = configurationStatus;
    }

    public int getSystemStoppedReleasedTicketCount() {
        return systemStoppedReleasedTicketCount;
    }

    public void setSystemStoppedReleasedTicketCount(int systemStoppedReleasedTicketCount) {
        this.systemStoppedReleasedTicketCount = systemStoppedReleasedTicketCount;
    }

    public int getSystemStoppedPoolSize() {
        return systemStoppedPoolSize;
    }

    public void setSystemStoppedPoolSize(int systemStoppedPoolSize) {
        this.systemStoppedPoolSize = systemStoppedPoolSize;
    }
}
