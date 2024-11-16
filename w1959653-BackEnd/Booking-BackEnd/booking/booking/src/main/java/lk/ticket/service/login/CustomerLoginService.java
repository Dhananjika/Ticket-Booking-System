package lk.ticket.service.login;

import lk.ticket.model.login.UserModule;
import lk.ticket.repository.login.LoginRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is the Customer Login Service Class. Here handel all the business logics of Customer Login.
 * This is inherited by LoginService abstract class.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@Service
public class CustomerLoginService extends LoginService {
    private static final Logger logger = Logger.getLogger(CustomerLoginService.class);

    @Autowired
    public CustomerLoginService(LoginRepository loginRepository) {
        super(loginRepository);
    }

    /**
     *  This method is used to register a user as customer
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
     *  This method is used to validate the username, password & email
     *
     *  @in  username, password, email
     *  @out validation error message or null(null means no issues with registration)
     * */
    @Override
    public String validateRegistration(UserModule userLogin) {
        logger.info("Method called");
        String validateMessage = null;
        List<UserModule> accountDetails = super.loginRepository.getAccountLoginDetails("customer");

        for (UserModule accountDetail : accountDetails) {
            validateMessage = (userLogin.getEmail().equals(accountDetail.getEmail())) ? "This email has already registered."
                    : ((userLogin.getUsername()).equals(accountDetail.getUsername()) && (userLogin.getPassword().equals(accountDetail.getPassword()))) ?
                    "This username password already in use." : null ;
        }
        logger.info(validateMessage);
        return validateMessage;
    }

    /**
     *  This method is used to user to login to the system as customer
     *
     *  @in  username, password
     *  @out login successful or unsuccessful
     * */
    @Override
    public String login(UserModule userModule) {
        logger.info("Method called");
        if(super.loginRepository.checkLogin("customer", userModule, "A")) {
            logger.info("Successfully logged in as Customer");
            return "Successfully logged in";
        }else {
            logger.warn("Invalid username or password");
            return null;
        }

    }

    /**
     *  This method is used to user to logout from the system as customer
     *
     *  @in  username, password
     *  @out logout successful or unsuccessful
     * */
    @Override
    public String logout(UserModule userModule) {
        logger.info("Method called");
        if(super.loginRepository.checkLogin("customer", userModule, "N")) {
            logger.info("Successfully logged out");
            return "Successfully logged out";
        }
        return null;
    }

}
