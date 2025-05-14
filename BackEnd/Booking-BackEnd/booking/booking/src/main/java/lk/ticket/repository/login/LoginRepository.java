package lk.ticket.repository.login;

import lk.ticket.model.login.UserModule;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This Repository class handles data access and persistence operations of User Management.</br></br>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@Repository
public class LoginRepository {
    private static final Logger logger = Logger.getLogger(LoginRepository.class);

    /**
     * This method is used to store register details in register data table.
     * <p></p>
     * @in UserLoginModule
     * @Exception SQLException, Exception
     * @out Successful or unsuccessful
     */
    public String userLoginRegister(UserModule userLogin) {
        logger.info("method called.");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        LocalDate localDate = LocalDate.now();
        try {
            connection = ConnectionManager.getConnection();
            String sql = null;
            if(connection != null) {
                if (userLogin.getUserType().equals("vendor")) {
                    logger.info("Insert data into register table as a vendor start");
                    List<String> vendorIds = getVendorIDList();
                    if (!vendorIds.contains(userLogin.getVendorID())) {
                        logger.warn("This vendor ID does not exist in this event. Please try again.");
                        return "This vendor ID does not exist in this event. Please try again.";
                    }
                    sql = "INSERT INTO ticket.register (login_type, username, password, vendor_id, signup_date,login_status) VALUES(?, ?, ?, ?, ?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, userLogin.getUserType());
                    preparedStatement.setString(2, userLogin.getUsername());
                    preparedStatement.setString(3, userLogin.getPassword());
                    preparedStatement.setString(4, userLogin.getVendorID());
                    preparedStatement.setString(5, localDate.toString());
                    preparedStatement.setString(6, "R");
                    int psCount = preparedStatement.executeUpdate();
                    ConnectionManager.commit(connection);
                    logger.info("Insert data into register table as a vendor commited psCount :" + psCount);
                    if (psCount > 0) {
                        checkLogin("vendor", userLogin, "R");
                        logger.info("You have successfully registered as Vendor");
                        return "You have successfully registered as Vendor!";
                    }
                } else if (userLogin.getUserType().equals("customer")) {
                    logger.info("Insert data into register table as a customer start");
                    sql = "INSERT INTO ticket.register (login_type, username, password, signup_date, customer_name, customer_email,login_status) VALUES(?, ?, ?, ?, ?, ?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, userLogin.getUserType());
                    preparedStatement.setString(2, userLogin.getUsername());
                    preparedStatement.setString(3, userLogin.getPassword());
                    preparedStatement.setString(4, localDate.toString());
                    preparedStatement.setString(5, userLogin.getName());
                    preparedStatement.setString(6, userLogin.getEmail());
                    preparedStatement.setString(7, "R");
                    int psCount = preparedStatement.executeUpdate();
                    ConnectionManager.commit(connection);
                    logger.info("Insert data into register table as a customer commited psCount :" + psCount);
                    if (psCount > 0) {
                        checkLogin("customer", userLogin, "R");
                        logger.info("You have successfully registered as Customer");
                        return "You have successfully registered as Customer!";
                    }
                }
            }
        }catch (SQLException se) {
           ConnectionManager.rollback(connection);
           logger.error("An error occurred while trying to insert the login details to login table" + se.getMessage());
           return "User registration failed";
        }catch (Exception e) {
           logger.error("An error occurred while trying to insert the login details to login table" + e.getMessage());
           return "User registration failed";
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return "User registration failed";
    }

    /**
     * This method is used to get all the registered account details.
     * <p></p>
     * @in userType
     * @Exception Exception
     * @out List of UserModule details
     */
    public List<UserModule> getAccountLoginDetails(String userType) {
        logger.info("method called.");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<UserModule> accountDetails = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String  sql = "SELECT login_type, username, password, vendor_id, signup_date, customer_name, customer_email FROM ticket.register WHERE login_type = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userType);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserModule userLoginModule = new UserModule();
                    userLoginModule.setUserType(resultSet.getString("login_type"));
                    userLoginModule.setUsername(resultSet.getString("username"));
                    userLoginModule.setPassword(resultSet.getString("password"));
                    userLoginModule.setVendorID(resultSet.getString("vendor_id"));
                    userLoginModule.setName(resultSet.getString("customer_name"));
                    userLoginModule.setEmail(resultSet.getString("customer_email"));
                    accountDetails.add(userLoginModule);
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while getting data from register table" + e.getMessage());
            return null;
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return accountDetails;
    }

    /**
     * This method is used to get all vendor ID list from vendor table.
     * <p></p>
     * @Exception Exception
     * @out List of vendor ID
     */
    public List<String> getVendorIDList(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> vendorIDList = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String  sql = "SELECT vendor_id FROM ticket.vendor;";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    vendorIDList.add(resultSet.getString("vendor_id"));
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while getting data from register table" + e.getMessage());
            return null;
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return vendorIDList;
    }

    /**
     * This method is used to check password and username is valid or not.
     * <p></p>
     * @in loginType, userModule, status
     * @Exception Exception
     * @out password and username is valid or not
     */
    public boolean checkLogin(String loginType, UserModule userModule, String status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String sql = "SELECT id,username, password FROM ticket.register WHERE login_type= ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, loginType);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getString("username").equals(userModule.getUsername())
                            && resultSet.getString("password").equals(userModule.getPassword())) {
                        updateLoginStatus(resultSet.getInt("id"), loginType, status);
                        userModule.setUserID(resultSet.getInt("id"));
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while checking login" + e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return false;
    }

    /**
     * This method is used to update the login_status (A-login, N-logout, R-Registered).
     * <p></p>
     * @in id, loginType, status
     * @Exception Exception
     * @out update the status in register table
     */
    public void updateLoginStatus(int id, String loginType, String status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String sql = "UPDATE ticket.register SET login_status = ? WHERE login_type = ? AND id = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, loginType);
                preparedStatement.setInt(3, id);
                int psCount = preparedStatement.executeUpdate();
                ConnectionManager.commit(connection);
                if (psCount > 0) {
                    logger.info("You have successfully updated the login status to " + status);
                }else {
                    logger.info("Status update failed");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating login status" + e.getMessage());
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
    }

    /**
     * This method is used to remove user account.
     * <p></p>
     * @in id
     * @Exception Exception
     * @out delete the record from register table
     */
    public String removeAccount(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String sql = "DELETE FROM ticket.register WHERE id = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                int psCount = preparedStatement.executeUpdate();
                ConnectionManager.commit(connection);
                if (psCount > 0) {
                    logger.info("You have successfully deleted the account " + id);
                    return "Successfully removed the account";
                }
            }
        }catch (SQLException se){
            logger.error("An error occurred while removing account" + se.getMessage());
            ConnectionManager.rollback(connection);
            return "Removing account failed";
        }catch (Exception e) {
            logger.error("An error occurred while removing account" + e.getMessage());
            return "Removing account failed";
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return "Removing account failed";
    }

    public UserModule getVendorLoginDetails(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserModule userModule = new UserModule();

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String sql = "select distinct vendor.vendor_name, vendor.email, vendor.vendor_id, register.id from vendor " +
                        "inner join register on register.vendor_id = vendor.vendor_id " +
                        "where register.username = ? and register.password = ? and login_type = 'vendor';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    userModule.setName(resultSet.getString("vendor_name"));
                    userModule.setEmail(resultSet.getString("email"));
                    userModule.setVendorID(resultSet.getString("vendor_id"));
                    userModule.setUserID(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while checking login" + e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        logger.info(userModule);
        return userModule;
    }

    public UserModule getCustomerLoginDetails(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserModule userModule = new UserModule();

        try {
            connection = ConnectionManager.getConnection();
            if(connection != null) {
                String sql = "select customer_name, customer_email, id from register where username = ? and password = ? and login_type = 'customer';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    userModule.setName(resultSet.getString("customer_name"));
                    userModule.setEmail(resultSet.getString("customer_email"));
                    userModule.setUserID(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while checking login" + e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        logger.info(userModule);
        return userModule;
    }
}
