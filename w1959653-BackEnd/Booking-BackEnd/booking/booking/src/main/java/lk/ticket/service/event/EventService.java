package lk.ticket.service.event;

import lk.ticket.model.event.EventModule;

import java.util.List;

public interface EventService {
    List<EventModule> getEvents();
}
