package lk.ticket.service;

import lk.ticket.model.Configuration;

public interface ConfigarationService {
    String submitConfiguration(Configuration configuration);
    String saveJsonFile(Configuration configuration);
    Configuration readJsonFile();
}
