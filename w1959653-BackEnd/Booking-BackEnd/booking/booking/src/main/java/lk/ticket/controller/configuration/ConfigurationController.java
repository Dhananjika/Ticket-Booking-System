package lk.ticket.controller.configuration;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.ConfigurationModule;
import lk.ticket.model.UserModule;
import lk.ticket.service.configuration.ConfigurationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller class handles incoming HTTP requests of configuration setup and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private static final Logger logger = Logger.getLogger(ConfigurationController.class);

    private final ConfigurationService configurationService;

    @Autowired
    private UserModule userModule;

    @Autowired
    public ConfigurationController(ConfigurationService configarationService) {
        this.configurationService = configarationService;
    }

    /**
     *  This method is used to test the connection
     *  @out resultMessage
     * */
    @GetMapping("/test")
    @Operation(summary = "Test", description = "Test the connection.")
    public String test(){
        logger.info(userModule.getUsername());
        return "Test";
    }

    /**
     *  This is the end point of submit the configuration details.</br>
     *  Used Mapping is - POST.</br>
     *  <a href="http://localhost:8080/configuration/submitConfiguration">Submit Configuration</a>
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @out resultMessage
     * */

    @PostMapping("/submitConfiguration")
    @Operation(summary = "Submit Configuration Parameters", description = "This used to set the configuration details. Use positive numbers.")
    public String submitConfiguration(@RequestBody ConfigurationModule configuration){

        logger.info("Method called");
        logger.info(configuration);
        return this.configurationService.submitConfiguration(configuration);
    }
}
