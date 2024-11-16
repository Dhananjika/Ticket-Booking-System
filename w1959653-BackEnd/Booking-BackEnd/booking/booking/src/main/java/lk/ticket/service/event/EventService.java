package lk.ticket.service.event;

import lk.ticket.model.event.EventModule;
import lk.ticket.model.event.VendorModule;

import java.util.List;

public interface EventService {
    String addEvent(EventModule event);
    String addVendorList(List<VendorModule> vendors);
}
