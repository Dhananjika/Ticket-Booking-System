package lk.ticket.controller.event;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.event.EventModule;
import lk.ticket.model.event.VendorModule;
import lk.ticket.model.login.UserModule;
import lk.ticket.service.event.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Controller class handles incoming HTTP requests of Event and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

@RestController
@RequestMapping("/event")
public class EventController {
    private static final Logger logger = Logger.getLogger(EventController.class);
    private final EventService eventService;
    private final EventModule eventModule = new EventModule();

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    private UserModule userModule;


    @PostMapping("/addEvent")
    @Operation(summary = "Add Event", description = "Add New Event.")
    public String addEvent(@RequestBody EventModule event) {
        logger.info("Method called");
        eventModule.setEventId(event.getEventId());
        userModule.setEventID(event.getEventId());
        if(userModule.getUsername().equals("admin") && userModule.getPassword().equals("admin123")) {
            return this.eventService.addEvent(event);
        }else{
            return "Only Admin can add event";
        }

    }

    @PostMapping("/addVendors")
    @Operation(summary = "Add Vendors", description = "Add vendors list of this event.")
    public String addVendors(@RequestBody List<VendorModule> vendors) {
        logger.info("Method called");
        if(userModule.getUsername().equals("admin") && userModule.getPassword().equals("admin123")) {
            return this.eventService.addVendorList(vendors);
        }else{
            return "Only Admin can add vendors";
        }
    }
}
