package lk.ticket.service.systemControl;

import lk.ticket.model.systemControl.SystemControlModule;

public interface SystemControlService {
    String startSystem(SystemControlModule systemControlModule, int id);
    String stopSystem(SystemControlModule systemControlModule, int id);
}
