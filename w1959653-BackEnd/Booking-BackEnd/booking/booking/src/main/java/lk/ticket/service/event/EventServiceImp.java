package lk.ticket.service.event;

import lk.ticket.model.event.EventModule;
import lk.ticket.model.event.VendorModule;
import lk.ticket.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImp(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public String addEvent(EventModule event) {
        return eventRepository.addEvent(event);
    }

    @Override
    public String addVendorList(List<VendorModule> vendors) {
        return eventRepository.addVendors(vendors);
    }
}
