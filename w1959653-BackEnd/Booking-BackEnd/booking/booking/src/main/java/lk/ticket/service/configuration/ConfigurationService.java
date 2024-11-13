package lk.ticket.service.configuration;

import lk.ticket.model.ConfigurationModule;

/**
 * This is the Configuration interface. This stores all abstract methods of configuration part
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public interface ConfigurationService {
    String submitConfiguration(ConfigurationModule configuration);
    String saveJsonFile(ConfigurationModule configuration);
    ConfigurationModule readJsonFile();
}
