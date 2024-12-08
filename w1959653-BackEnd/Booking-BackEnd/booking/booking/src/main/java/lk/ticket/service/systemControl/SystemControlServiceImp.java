package lk.ticket.service.systemControl;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.repository.configuration.SystemControlRepository;
import lk.ticket.service.ticketPool.TicketPoolService;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemControlServiceImp implements SystemControlService {

    @Autowired
    private SystemControlRepository systemControlRepository;

    @Override
    public String startSystem(SystemControlModule systemControlModule, int id, TicketPoolService ticketPoolService) {
        SystemControlModule systemControlModuleExist = systemControlRepository.getSystemConfiguration(id);
        if(systemControlModuleExist.getSystemStatus().equals("I")){
            systemControlModule.setSystemStoppedReleasedTicketCount(systemControlModuleExist.getSystemStoppedReleasedTicketCount());
            systemControlModule.setSystemStoppedPoolSize(systemControlModuleExist.getSystemStoppedPoolSize());

            ticketPoolService.resumeTicketPool(systemControlModuleExist.getSystemStoppedReleasedTicketCount(), systemControlModuleExist.getSystemStoppedPoolSize());
            String message = systemControlRepository.addConfiguration(systemControlModule, id);
            if (message != null) {
                return "System Started";
            }else {
                return "System Start Failed";
            }
        }else{
            return "System Already Started";
        }
    }

    @Override
    public String stopSystem(SystemControlModule systemControlModule, int id) {
        SystemControlModule systemControlModuleExist = systemControlRepository.getSystemConfiguration(id);
        if(systemControlModuleExist.getSystemStatus().equals("A")){
            String message = systemControlRepository.addConfiguration(systemControlModule, id);
            if (message != null) {
                return "System Stopped";
            }else {
                return "System Stop Failed";
            }
        }else{
            return "System Already Stopped";
        }
    }

    @Override
    public SystemControlModule getSystemStatus(int id) {
        return systemControlRepository.getSystemConfiguration(id);
    }


}
