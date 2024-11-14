package lk.ticket.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@SessionScope
@Component
public class UserModule {
    private String username;
    private String password;
    private String userType;
    private String vendorID;
    private String name;
    private String email;
    private List<UserModule> vendorList;
    private List<UserModule> customerList;
    private int userID;

    public UserModule(){}

    public UserModule(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     *  This method is used to display the Login variable values
     * */
    @Override
    public String toString() {
        return "UserLoginModule [username=" + username + ", password=" + password + ", userType=" + userType + ", vendorID=" + vendorID+ ", customerName=" + name + ", customerEmail=" + email + "]";
    }

    //Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserModule> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<UserModule> vendorList) {
        this.vendorList = vendorList;
    }

    public List<UserModule> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<UserModule> customerList) {
        this.customerList = customerList;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
