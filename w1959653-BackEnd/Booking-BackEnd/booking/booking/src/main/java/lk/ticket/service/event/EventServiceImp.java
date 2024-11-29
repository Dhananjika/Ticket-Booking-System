package lk.ticket.service.event;

import lk.ticket.model.event.EventModule;
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

    public List<EventModule> getEvents(){
        return eventRepository.getEvents();
    }
}
