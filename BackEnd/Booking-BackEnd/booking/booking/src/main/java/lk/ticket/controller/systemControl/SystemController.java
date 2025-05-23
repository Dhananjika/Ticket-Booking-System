package lk.ticket.controller.systemControl;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.login.UserModule;
import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.service.systemControl.SystemControlService;
import lk.ticket.service.ticketPool.TicketPoolService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller class handles incoming HTTP requests of system control and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/systemControl")
public class SystemController {
    private static final Logger logger = Logger.getLogger(SystemController.class);
    private final SystemControlModule systemControlModule = new SystemControlModule();
    private final SystemControlService systemControlService;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private UserModule userModule;

    @Autowired
    public SystemController(SystemControlService systemControlService) {
        this.systemControlService = systemControlService;
    }

    @PostMapping("/startSystem")
    @Operation(summary = "Start System", description = "Start the system ticketing control.")
    public String startSystem(@RequestParam int eventID) {
        logger.info("Method called");

        systemControlModule.setConfigurationStatus("A");
        systemControlModule.setSystemStatus("A");
        return systemControlService.startSystem(systemControlModule, eventID, ticketPoolService);
    }

    @PostMapping("/stopSystem")
    @Operation(summary = "Stop System", description = "Stop the system ticketing control.")
    public String stopSystem(@RequestParam int eventID) {
        logger.info("Method called");

        systemControlModule.setConfigurationStatus("A");
        systemControlModule.setSystemStatus("I");
        systemControlModule.setSystemStoppedReleasedTicketCount(ticketPoolService.getReleasedTicketCount());
        systemControlModule.setSystemStoppedPoolSize(ticketPoolService.getAvailableTicketsCount(eventID));
        return systemControlService.stopSystem(systemControlModule, eventID);
    }

    @GetMapping("/systemStatus")
    public SystemControlModule getSystemStatus(int eventID){
        logger.info("Method called");
        return systemControlService.getSystemStatus(eventID);
    }
}
