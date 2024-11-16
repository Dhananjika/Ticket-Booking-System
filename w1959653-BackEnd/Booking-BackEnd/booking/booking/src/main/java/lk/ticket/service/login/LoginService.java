package lk.ticket.service.login;

import lk.ticket.model.login.UserModule;
import lk.ticket.repository.login.LoginRepository;

/**
 * This is the Vendor Login abstract Class. Here store all the common methods for login.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public abstract class LoginService {
    protected LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public abstract String register(UserModule userModule);

    public abstract String login(UserModule userModule);

    public abstract String logout(UserModule userModule);

    public String validateRegistration(UserModule userLogin) {
        return null;
    }
}
