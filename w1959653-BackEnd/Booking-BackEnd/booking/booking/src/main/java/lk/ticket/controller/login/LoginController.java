package lk.ticket.controller.login;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.UserModule;
import lk.ticket.repository.login.LoginRepository;
import lk.ticket.service.login.CustomerLoginService;
import lk.ticket.service.login.LoginService;
import lk.ticket.service.login.VendorLoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * This Controller class handles incoming HTTP requests of login and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private VendorLoginService vendorLogin;

    @Autowired
    private CustomerLoginService customerLogin;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserModule userModule;

    @Autowired
    private HttpSession session;

    @PostMapping("/vendorRegister")
    @Operation(summary = "Vendor Registration", description = "User can register as vendor. But person should be already registered to the company.")
    public String vendorRegister(@RequestParam String username, @RequestParam String password,@RequestParam String vendorID) {
        logger.info("Method called");
        userModule.setUsername(username);
        userModule.setPassword(password);
        userModule.setVendorID(vendorID);
        userModule.setUserType("vendor");
        logger.info(userModule);
        LoginService vendorLogin = new VendorLoginService(loginRepository);
        return vendorLogin.register(userModule);
    }

    @PostMapping("/customerRegister")
    @Operation(summary = "Customer Registration", description = "User can register as customer.")
    public String customerRegister(@RequestParam String username, @RequestParam String password,@RequestParam String customerName, @RequestParam String customerEmail) {
        logger.info("Method called");
        userModule.setUsername(username);
        userModule.setPassword(password);
        userModule.setName(customerName);
        userModule.setEmail(customerEmail);
        userModule.setUserType("customer");
        logger.info(userModule);
        LoginService customerLoginService = new CustomerLoginService(loginRepository);
        return customerLoginService.register(userModule);
    }

    @PostMapping("/userLogin")
    @Operation(summary = "User Login", description = "User can login to the system.")
    public String userLogin(@RequestParam String username, @RequestParam String password) {
        logger.info("Method called");
        userModule.setUsername(username);
        userModule.setPassword(password);
        logger.info(userModule);
        LoginService customerLogin = new CustomerLoginService(loginRepository);
        LoginService vendorLogin = new VendorLoginService(loginRepository);
        String login = customerLogin.login(userModule);
        if (login == null) {
            login = vendorLogin.login(userModule);
            if (login == null) {
                return "Invalid username or password";
            }
        }
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setMaxInactiveInterval(15 * 60); // 15 minutes session timeout
        return login;
    }


    @GetMapping("/userLoginOut")
    @Operation(summary = "User Logout", description = "User can logout from the system.")
    public String userLogOut() {
        logger.info("Method called");
        logger.info(userModule);
        String login = customerLogin.logout(userModule);
        if (login == null) {
            login = vendorLogin.logout(userModule);
            if (login == null) {
                return "Invalid username or password";
            }
        }
        if (session != null) {
            session.invalidate();
        }
        return login;
    }

    @GetMapping("/removeAccount")
    @Operation(summary = "Remove User Account", description = "User can remove their user account from the system. After remove if user wants to login again user have to register to system again")
    public String removeAccount() {
        logger.info("Method called");
        logger.info(userModule);
        String removeAccount = vendorLogin.removeAccount(userModule.getUserID());
        if (session != null) {
            session.invalidate();
        }
        return removeAccount;
    }

}
