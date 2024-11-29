package lk.ticket.controller.event;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.event.EventModule;
import lk.ticket.service.event.EventService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * This Controller class handles incoming HTTP requests of Event setup and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getEvents")
    @Operation(summary = "Event", description = "Get all details of all events.")
    public List<EventModule> getEvents(){
        return eventService.getEvents();
    }
}
