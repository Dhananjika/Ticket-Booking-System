package lk.ticket.service.systemControl;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;

public interface SystemControlService {
    String startSystem(SystemControlModule systemControlModule, int id, TicketPoolServiceImp ticketPoolServiceImp);
    String stopSystem(SystemControlModule systemControlModule, int id);
}
