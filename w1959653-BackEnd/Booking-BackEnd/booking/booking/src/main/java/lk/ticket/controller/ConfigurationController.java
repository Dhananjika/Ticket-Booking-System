package lk.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.Configuration;
import lk.ticket.service.ConfigarationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private static final Logger logger = Logger.getLogger(ConfigurationController.class);

    private final ConfigarationService configarationService;

    @Autowired
    public ConfigurationController(ConfigarationService configarationService) {
        this.configarationService = configarationService;
    }

    /**
     *  This method is used to test the connection
     *  @out resultMessage
     * */
    @GetMapping("/test")
    @Operation(summary = "Test", description = "Test the connection.")
    public String test(){
        logger.info("Test Configuration");
        return "Test Configuration";
    }

    /**
     *  This method is used to submit the configuration details
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @out resultMessage
    * */

    @PostMapping("/submitConfiguration")
    @Operation(summary = "Submit Configuration Parameters", description = "This used to set the configuration details. Use positive numbers.")
    public String SubmitConfiguration(@RequestBody Configuration configuration){

        logger.info("Method called");
        logger.info(configuration);
        return configarationService.submitConfiguration(configuration);
    }
}
