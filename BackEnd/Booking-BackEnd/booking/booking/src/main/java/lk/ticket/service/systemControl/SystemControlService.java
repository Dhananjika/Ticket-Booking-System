package lk.ticket.service.systemControl;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.service.ticketPool.TicketPoolService;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;

public interface SystemControlService {
    String startSystem(SystemControlModule systemControlModule, int id, TicketPoolService ticketPoolService);
    String stopSystem(SystemControlModule systemControlModule, int id);
    SystemControlModule getSystemStatus(int id);
}
