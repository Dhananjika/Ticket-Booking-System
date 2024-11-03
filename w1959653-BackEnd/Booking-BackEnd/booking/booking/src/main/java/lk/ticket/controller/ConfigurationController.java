package lk.ticket.controller;

import lk.ticket.model.Configuration;
import lk.ticket.service.ConfigarationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private static final Logger logger = Logger.getLogger(ConfigurationController.class);
    private String resultMessage;

    private final ConfigarationService configarationService;

    @Autowired
    public ConfigurationController(ConfigarationService configarationService) {
        this.configarationService = configarationService;
    }

    /* This method is used to test the connection */
    @GetMapping("/test")
    public String test(){
        logger.info("Test Configuration");
        return "Test Configuration";
    }

    /**
     *  This method is used to submit the configuration details
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @throws Exception
     *  @out resultMessage
    * */

    @PostMapping("/submitConfiguration")
    public String SubmitConfiguration(@RequestBody Configuration configuration){
        logger.info("Method called");
        logger.info(configuration);
        resultMessage =  configarationService.submitConfiguration(configuration, "Admin");
        return resultMessage;
    }
}
