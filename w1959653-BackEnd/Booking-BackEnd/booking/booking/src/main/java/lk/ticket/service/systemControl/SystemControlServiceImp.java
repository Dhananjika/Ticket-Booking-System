package lk.ticket.service.systemControl;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.repository.configuration.SystemControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemControlServiceImp implements SystemControlService {

    @Autowired
    private SystemControlRepository systemControlRepository;

    @Override
    public String startSystem(SystemControlModule systemControlModule, int id) {
        SystemControlModule systemControlModuleExist = systemControlRepository.getSystemConfiguration(id);
        systemControlModule.setSystemStoppedReleasedTicketCount(systemControlModuleExist.getSystemStoppedReleasedTicketCount());
        systemControlModule.setSystemStoppedPoolSize(systemControlModuleExist.getSystemStoppedPoolSize());

        String message = systemControlRepository.addConfiguration(systemControlModule, id);
        if (message != null) {
            return "System Started";
        }else {
            return "System Start Failed";
        }
    }

    @Override
    public String stopSystem(SystemControlModule systemControlModule, int id) {
        String message = systemControlRepository.addConfiguration(systemControlModule, id);
        if (message != null) {
            return "System Stopped";
        }else {
            return "System Stop Failed";
        }
    }
}
