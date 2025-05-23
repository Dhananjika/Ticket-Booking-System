package lk.ticket.model.event;

public class EventModule {
    private int eventId;
    private String eventName;
    private String eventLocation;
    private String eventType;
    private String eventDate;
    private String eventTime;
    private int eventNormalTicketPrice;
    private int eventVIPTicketPrice;
    private String eventImage;
    private String systemStatus;
    private String configurationStatus;

    public EventModule() {}

    public String toString(){
        return "EventModule [eventId=" + eventId + ", eventName=" + eventName + ", eventLocation=" + eventLocation + ", eventType=" + eventType + ", eventDate=" + eventDate + ", eventTime=" + eventTime + ", eventNormalTicketPrice=" + eventNormalTicketPrice + ", eventVIPTicketPrice=" + eventVIPTicketPrice + ", eventImage=" + eventImage + ", System Status=" + systemStatus +", Configuration status="+ configurationStatus +"]";
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventNormalTicketPrice() {
        return eventNormalTicketPrice;
    }

    public void setEventNormalTicketPrice(int eventNormalTicketPrice) {
        this.eventNormalTicketPrice = eventNormalTicketPrice;
    }

    public int getEventVIPTicketPrice() {
        return eventVIPTicketPrice;
    }

    public void setEventVIPTicketPrice(int eventVIPTicketPrice) {
        this.eventVIPTicketPrice = eventVIPTicketPrice;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

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
}
