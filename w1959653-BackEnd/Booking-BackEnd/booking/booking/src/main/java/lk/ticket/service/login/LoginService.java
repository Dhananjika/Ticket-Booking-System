package lk.ticket.service.login;

import lk.ticket.model.UserModule;
import lk.ticket.repository.LoginRepository;

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
