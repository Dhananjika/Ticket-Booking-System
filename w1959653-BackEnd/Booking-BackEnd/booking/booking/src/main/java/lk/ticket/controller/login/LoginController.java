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
    private final VendorLoginService vendorLogin;
    private final CustomerLoginService customerLogin;
    private final LoginRepository loginRepository = new LoginRepository();

    @Autowired
    public LoginController(VendorLoginService vendorLogin, CustomerLoginService customerLogin) {
        this.vendorLogin = vendorLogin;
        this.customerLogin = customerLogin;
    }

    @PostMapping("/vendorRegister")
    @Operation(summary = "Vendor Registration", description = "User can register as vendor. But person should be already registered to the company.")
    public String vendorRegister(@RequestParam String username, @RequestParam String password,@RequestParam String vendorID) {
        logger.info("Method called");
        UserModule vendorRegistration = new UserModule(username, password,"vendor",vendorID);
        logger.info(vendorRegistration);
        LoginService vendorLogin = new VendorLoginService(loginRepository);
        return vendorLogin.register(vendorRegistration);
    }

    @PostMapping("/customerRegister")
    @Operation(summary = "Customer Registration", description = "User can register as customer.")
    public String customerRegister(@RequestParam String username, @RequestParam String password,@RequestParam String customerName, @RequestParam String customerEmail) {
        logger.info("Method called");
        UserModule customerRegisterModule = new UserModule(username, password, "customer",customerName,customerEmail);
        logger.info(customerRegisterModule);
        LoginService customerLoginService = new CustomerLoginService(loginRepository);
        return customerLoginService.register(customerRegisterModule);
    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String username, @RequestParam String password) {
        logger.info("Method called");
        UserModule userModule = new UserModule(username, password);
        logger.info(userModule);
        String login = customerLogin.login(userModule);
        if (login == null) {
            login = vendorLogin.login(userModule);
            if (login == null) {
                return "Invalid username or password";
            }
        }
        return login;
    }

}
