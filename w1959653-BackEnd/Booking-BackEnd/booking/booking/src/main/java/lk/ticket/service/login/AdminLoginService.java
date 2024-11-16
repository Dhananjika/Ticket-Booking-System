package lk.ticket.service.login;

import lk.ticket.model.login.UserModule;
import lk.ticket.repository.login.LoginRepository;
import org.apache.log4j.Logger;

public class AdminLoginService extends LoginService {
    private static final Logger logger = Logger.getLogger(AdminLoginService.class);

    public AdminLoginService(LoginRepository loginRepository) {
        super(loginRepository);
    }

    @Override
    public String register(UserModule userModule) {
        return null;
    }

    /**
     *  This method is used to user to login to the system as admin
     *
     *  @in  username, password
     *  @out login successful or unsuccessful
     * */
    @Override
    public String login(UserModule userModule) {
        logger.info("Method called");
        if (userModule.getUsername().equals("admin") && userModule.getPassword().equals("admin123")) {
            logger.info("Successfully logged in as Admin");
            return "Successfully logged in as Admin";
        }
        logger.warn("Invalid username or password");
        return null;
    }

    /**
     *  This method is used to user to logout from the system as admin
     *
     *  @in  username, password
     *  @out logout successful or unsuccessful
     * */
    @Override
    public String logout(UserModule userModule) {
        logger.info("Method called");
        if(userModule.getUsername().equals("admin") && userModule.getPassword().equals("admin123")) {
            logger.info("Successfully logged out");
            return "Successfully logged out";
        }
        return null;
    }
}
