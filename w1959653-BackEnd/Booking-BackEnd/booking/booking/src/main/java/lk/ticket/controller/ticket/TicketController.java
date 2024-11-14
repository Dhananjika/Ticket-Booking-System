package lk.ticket.controller.ticket;

import io.swagger.v3.oas.annotations.Operation;
import lk.ticket.model.UserModule;
import lk.ticket.service.configuration.ConfigurationServiceImp;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import lk.ticket.service.userThread.CustomerService;
import lk.ticket.service.userThread.VendorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketController")
public class TicketController {
    private static final Logger logger = Logger.getLogger(TicketController.class);

    @Autowired
    private UserModule userModule;

    @Autowired
    private TicketPoolServiceImp ticketPoolService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConfigurationServiceImp configurationServiceImp;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/addTicket")
    @Operation(summary = "Add Ticket", description = "Vendor release tickets to ticket pool.")
    public String addTicket() {
        logger.info("Method called");
        ticketPoolService = new TicketPoolServiceImp(configurationServiceImp.readJsonFile());

        vendorService = new VendorService(ticketPoolService);
        Thread vendorThread = new Thread(vendorService);
        vendorThread.start();

        try {
            vendorThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for vendor thread to finish: " + e.getMessage());
        }
        messagingTemplate.convertAndSend("/topic/ticketCount", ticketPoolService.getAvailableTicketsCount());
        return vendorService.getReturnMessage();
    }

    @GetMapping("/purchaseTicket")
    @Operation(summary = "Purchase Ticket", description = "Customer purchase tickets from the system.")
    public String purchaseTicket(@RequestParam int ticketCount) {
        logger.info("Method called");
        ticketPoolService = new TicketPoolServiceImp(configurationServiceImp.readJsonFile());

        customerService = new CustomerService(ticketPoolService);
        customerService.setTicketCount(ticketCount);
        Thread customerThread = new Thread(customerService);
        customerThread.start();

        try {
            customerThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for customer thread to finish: " + e.getMessage());
        }
        messagingTemplate.convertAndSend("/topic/ticketCount", ticketPoolService.getAvailableTicketsCount());
        return customerService.getReturnMessage();
    }

    @PostMapping("/confirmAdding")
    @Operation(summary = "Confirm Ticket Adding", description = "The confirmation box is provided on the first attempt to collect the ticket system. After confirmation proceed this will work.")
    public String addTicketConfirm() {
        logger.info("Method called");

        vendorService = new VendorService(ticketPoolService);
        vendorService.setAddTickets(true);
        Thread vendorThread = new Thread(vendorService);
        vendorThread.start();

        try {
            vendorThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for vendor thread to finish: " + e.getMessage());
        }
        messagingTemplate.convertAndSend("/topic/ticketCount", ticketPoolService.getAvailableTicketsCount());
        return vendorService.getReturnMessage();
    }

    @PostMapping("/confirmPurchase")
    @Operation(summary = "Confirm Ticket Purchase", description = "The confirmation box is provided on the first attempt to purchase tickets from the ticket system. After confirmation proceed this will work.")
    public String purchaseTicketConfirm(@RequestParam int ticketCount) {
        logger.info("Method called");

        customerService = new CustomerService(ticketPoolService);
        customerService.setPurchaseTicket(true);
        customerService.setTicketCount(ticketCount);
        Thread customerThread = new Thread(customerService);
        customerThread.start();

        try {
            customerThread.join();
        }catch (InterruptedException e){
            logger.error("An error occurred while waiting for customer thread to finish: " + e.getMessage());
        }
        messagingTemplate.convertAndSend("/topic/ticketCount", ticketPoolService.getAvailableTicketsCount());
        return customerService.getReturnMessage();
    }
}
