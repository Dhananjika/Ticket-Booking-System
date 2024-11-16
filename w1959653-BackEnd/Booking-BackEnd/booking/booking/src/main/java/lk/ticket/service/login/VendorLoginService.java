package lk.ticket.service.login;

import lk.ticket.model.login.UserModule;
import lk.ticket.repository.login.LoginRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is the Vendor Login Service Class. Here handel all the business logics of Vendor Login.
 * This is inherited by LoginService abstract class.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@Service
public class VendorLoginService extends LoginService {
    private static final Logger logger = Logger.getLogger(VendorLoginService.class);

    @Autowired
    public VendorLoginService(LoginRepository loginRepository) {
        super(loginRepository);
    }

    /**
     *  This method is used to register a user as vendor
     *
     *  @in  UserModule
     *  @out registration successful or unsuccessful
     * */
    @Override
    public String register(UserModule userLogin) {
        logger.info("Method called");
        String validateMessage = validateRegistration(userLogin);
        if (validateMessage != null) {
            return validateMessage;
        }
        return super.loginRepository.userLoginRegister(userLogin);
    }

    /**
     *  This method is used to validate the username, password & Vendor ID
     *
     *  @in  username, password, Vendor ID
     *  @out validation error message or null(null means no issues with registration)
     * */
    @Override
    public String validateRegistration(UserModule userLogin) {
        logger.info("Method called");
        String validateMessage = null;
        List<UserModule> accountDetails = loginRepository.getAccountLoginDetails("vendor");
        for (UserModule accountDetail : accountDetails) {
            validateMessage = (userLogin.getVendorID().equals(accountDetail.getVendorID())) ? "Vendor ID has already registered to the system."
                    : ((userLogin.getUsername()).equals(accountDetail.getUsername()) && (userLogin.getPassword().equals(accountDetail.getPassword()))) ?
                    "This username password already in use." : null ;
        }
        logger.info(validateMessage);
        return validateMessage;
    }

    /**
     *  This method is used to user to login to the system as vendor
     *
     *  @in  username, password
     *  @out login successful or unsuccessful
     * */
    @Override
    public String login(UserModule userModule) {
        logger.info("Method called");
        if(loginRepository.checkLogin("vendor", userModule, "A")) {
            logger.info("Successfully logged in as Vendor");
            return "Successfully logged in as Vendor";
        }else {
            logger.warn("Invalid username or password");
            return null;
        }
    }

    /**
     *  This method is used to user to logout from the system as vendor
     *
     *  @in  username, password
     *  @out logout successful or unsuccessful
     * */
    @Override
    public String logout(UserModule userModule) {
        logger.info("Method called");
        if(loginRepository.checkLogin("vendor", userModule, "N")) {
            logger.info("Successfully logged out");
            return "Successfully logged out";
        }
        return null;
    }

    /**
     *  This method is used to user to remove their account from the system
     *
     *  @in  id
     *  @out remove account successful or unsuccessful
     * */
    public String removeAccount(int id){
        logger.info("Method called");
        if(id == 0){
            return "Please login to the system";
        }
        return loginRepository.removeAccount(id);
    }
}
