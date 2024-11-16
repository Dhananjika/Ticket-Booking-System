package lk.ticket.model.event;

public class EventModule {
    private int eventId;
    private String eventName;
    private String eventType;
    private String eventLocation;
    private String eventDate;
    private String eventTime;

    public EventModule() {}

    public EventModule(int eventId, String eventName, String eventType, String eventLocation, String eventDate, String eventTime) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }



    public String toString(){
        return "EventModule[Event ID - " + eventId + ", Event Name - " + eventName + ", Event Type - " + eventType + ", Event Location - " + eventLocation + ", Event Date - " + eventDate + ", Event Time - " + eventTime + "]";
    }

    //Getters & Setters
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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
}
