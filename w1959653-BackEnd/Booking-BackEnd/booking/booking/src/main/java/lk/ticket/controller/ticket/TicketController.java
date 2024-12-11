package lk.ticket.controller.ticket;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.login.UserModule;
import lk.ticket.service.configuration.ConfigurationServiceImp;
import lk.ticket.service.ticketPool.TicketPoolService;
import lk.ticket.service.userThread.CustomerService;
import lk.ticket.service.userThread.VendorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This Controller class handles incoming HTTP requests of ticket controlling and directs them
 * to the appropriate service methods.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/ticketController")
public class TicketController {
    private static final Logger logger = Logger.getLogger(TicketController.class);
    private final TicketPoolService ticketPoolService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConfigurationServiceImp configurationServiceImp;

    @Autowired
    private UserModule userModule;

    @Autowired
    public TicketController(TicketPoolService ticketPoolService, SimpMessagingTemplate messagingTemplate) {
        this.ticketPoolService = ticketPoolService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     *  This is the end point of adding tickets to the system.</br>
     *  Used Mapping is - POST.</br>
     *  <a href="http://localhost:8080/ticketController/addTicket">Add Tickets</a>
     *
     *  @out added ticket count or confirmation message.
     * */
    @PostMapping("/addTicket")
    @Operation(summary = "Add Ticket", description = "Vendor release tickets to ticket pool.")
    public String addTicket(@RequestParam int eventID, @RequestParam String username) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID), eventID);
        logger.info(userModule);

        VendorService vendorService = new VendorService(ticketPoolService, eventID);
        vendorService.setAddTickets(false);

        Thread vendorThread = new Thread(vendorService, username);
        vendorThread.start();

        try {
            vendorThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for vendor thread to finish: " + e.getMessage());
        }
        return vendorService.getReturnMessage();
    }

    /**
     *  This is the end point of purchasing tickets from the system.</br>
     *  Used Mapping is - POST.</br>
     *  <a href="http://localhost:8080/ticketController/purchaseTicket">Purchase Ticket</a>
     *
     *  @out purchased ticket count or confirmation message.
     * */
    @PostMapping("/purchaseTicket")
    @Operation(summary = "Purchase Ticket", description = "Customer purchase tickets from the system.")
    public String purchaseTicket(@RequestParam int ticketCount, @RequestParam int eventID, @RequestParam String username) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID), eventID);
        logger.info(userModule);

        CustomerService customerService = new CustomerService(ticketPoolService, eventID);
        customerService.setTicketCount(ticketCount);
        customerService.setPurchaseTicket(false);

        Thread customerThread = new Thread(customerService, username);
        customerThread.start();

        try {
            customerThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for customer thread to finish: " + e.getMessage());
        }
        return customerService.getReturnMessage();
    }

    /**
     *  This is the end point of adding tickets to the system after confirmation.</br>
     *  Used Mapping is - POST.</br>
     *  <a href="http://localhost:8080/ticketController/confirmAdding">Confirm Ticket Adding</a>
     *
     *  @out added ticket count.
     * */
    @PostMapping("/confirmAdding")
    @Operation(summary = "Confirm Ticket Adding", description = "The confirmation box is provided on the first attempt to collect the ticket system. After confirmation proceed this will work.")
    public String addTicketConfirm(@RequestParam int eventID, @RequestParam String username) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID), eventID);

        VendorService vendorService = new VendorService(ticketPoolService, eventID);
        vendorService.setAddTickets(true);

        Thread vendorThread = new Thread(vendorService, username);
        vendorThread.start();

        try {
            vendorThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for vendor thread to finish: " + e.getMessage());
        }
        return vendorService.getReturnMessage();
    }

    /**
     *  This is the end point of purchasing tickets from the system after confirmation.</br>
     *  Used Mapping is - POST.</br>
     *  <a href="http://localhost:8080/ticketController/confirmPurchase">Confirm Ticket Purchase</a>
     *
     *  @out purchased ticket count.
     * */
    @PostMapping("/confirmPurchase")
    @Operation(summary = "Confirm Ticket Purchase", description = "The confirmation box is provided on the first attempt to purchase tickets from the ticket system. After confirmation proceed this will work.")
    public String purchaseTicketConfirm(@RequestParam int eventID, @RequestParam String username) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID), eventID);

        CustomerService customerService = new CustomerService(ticketPoolService,eventID);
        customerService.setPurchaseTicket(true);

        Thread customerThread = new Thread(customerService,username);
        customerThread.start();

        try {
            customerThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for customer thread to finish: " + e.getMessage());
        }
        return customerService.getReturnMessage();
    }

    /**
     * For Testing purpose of multithreading - vendor
     */

    @PostMapping("/addTicketTest")
    @Operation(summary = "Add Ticket Test", description = "Vendor release tickets to ticket pool. - Multithreading Test")
    public String addTicketTest(@RequestParam int eventID) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID),eventID);
        logger.info(userModule);

        VendorService vendorService = new VendorService(ticketPoolService, eventID);
        vendorService.setAddTickets(false);

        List<Thread> vendorThreads = new ArrayList<Thread>();
        for(int i = 0; i < 10; i++){
            Thread vendorThread = new Thread(vendorService, "Vendor Tread : " + (i + 1));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        try {
            for(Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for vendor thread to finish: " + e.getMessage());
        }
        return vendorService.getReturnMessage();
    }

    /**
     * For Testing purpose of multithreading - customer
     */
    @PostMapping("/purchaseTicketTest")
    @Operation(summary = "Purchase Ticket Test", description = "Customer purchase tickets from the system. - - Multithreading Test")
    public String purchaseTicketTest(@RequestParam int ticketCount,@RequestParam int eventID) {
        logger.info("Method called");
        ticketPoolService.setConfigurationModule(configurationServiceImp.readJsonFile(eventID),eventID);
        logger.info(userModule);

        CustomerService customerService = new CustomerService(ticketPoolService, eventID);
        customerService.setTicketCount(ticketCount);
        customerService.setPurchaseTicket(false);


        List<Thread> customerThreads = new ArrayList<Thread>();
        for(int i = 0; i < 10; i++){
            Thread customerThread = new Thread(customerService,"Customer Tread : " + (i + 1));
            customerThreads.add(customerThread);
            customerThread.start();
        }

        try {
            for(Thread customerThread : customerThreads) {
                customerThread.join();
            }
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for customer thread to finish: " + e.getMessage());
        }
        return customerService.getReturnMessage();
    }

    @GetMapping("/getTicketStatus")
    @Operation(summary = "Get Ticket Count", description = "Get Ticket Pool Status")
    public int getTicketCount(@RequestParam int eventID) {
        return ticketPoolService.getAvailableTicketsCount(eventID);
    }
}
