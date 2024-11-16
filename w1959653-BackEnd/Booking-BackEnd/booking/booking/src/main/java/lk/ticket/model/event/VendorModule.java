package lk.ticket.model.event;

public class VendorModule {
    private String vendorID;
    private String vendorName;
    private String vendorEmail;
    private int eventID;

    public VendorModule(String vendorID, String vendorName, String vendorEmail) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
    }

    public String toString() {
        return "VendorModule[Vendor ID - " + vendorID + ", Vendor Name - " + vendorName + ", Vendor Email - " + vendorEmail + "]";
    }

    //Getters && Setters

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
