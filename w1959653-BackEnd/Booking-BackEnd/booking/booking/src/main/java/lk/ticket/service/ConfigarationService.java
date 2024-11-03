package lk.ticket.service;

import lk.ticket.model.Configuration;

public interface ConfigarationService {
    public String submitConfiguration(Configuration configuration, String user);
    public String saveJsonFile(Configuration configuration);
    public Configuration readJsonFile();
}
