package lk.ticket.service.login;

import lk.ticket.model.UserModule;
import lk.ticket.repository.login.LoginRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorLoginService extends LoginService {
    private static final Logger logger = Logger.getLogger(VendorLoginService.class);

    @Autowired
    public VendorLoginService(LoginRepository loginRepository) {
        super(loginRepository);
    }

    @Override
    public String register(UserModule userLogin) {
        logger.info("Method called");
        String validateMessage = validateRegistration(userLogin);
        if (validateMessage != null) {
            return validateMessage;
        }
        return super.loginRepository.userLoginRegister(userLogin);
    }

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

    @Override
    public String login(UserModule userModule) {
        logger.info("Method called");
        if(loginRepository.checkLogin("vendor", userModule, "A")) {
            logger.info("Successfully logged in as Vendor");
            return "Successfully logged in";
        }else {
            logger.warn("Invalid username or password");
            return null;
        }
    }

    @Override
    public String logout(UserModule userModule) {
        logger.info("Method called");
        if(loginRepository.checkLogin("vendor", userModule, "N")) {
            logger.info("Successfully logged out");
            return "Successfully logged out";
        }
        return null;
    }

    public String removeAccount(int id){
        logger.info("Method called");
        if(id == 0){
            return "Please login to the system";
        }
        return loginRepository.removeAccount(id);
    }
}
