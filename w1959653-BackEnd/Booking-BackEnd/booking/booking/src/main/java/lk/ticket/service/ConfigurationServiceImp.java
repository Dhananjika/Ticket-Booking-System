package lk.ticket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.ticket.model.Configuration;
import lk.ticket.util.PropertyReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ConfigurationServiceImp implements ConfigarationService{
    private static final Logger logger = Logger.getLogger(ConfigurationServiceImp.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     *  This method is used to submit the configuration details
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @out resultMessage
     * */

    @Override
    public String submitConfiguration(Configuration configuration){
        logger.info("Method called");
        String returnMessage = validateConfiguration(configuration, "Total Tickets");

        if(returnMessage == null){
            returnMessage = validateConfiguration(configuration, "Maximum Tickets");

            if(returnMessage == null){
                returnMessage = validateConfiguration(configuration, "Ticket Release Rate");

                if (returnMessage == null){
                    returnMessage = validateConfiguration(configuration, "Customer Rate");

                    if (returnMessage == null){
                        returnMessage = saveJsonFile(configuration);
                    }
                }
            }
        }
        return returnMessage;
    }

    /**
     *  This method is used to save the configuration details to json file
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @Exception IOException
     *  @out jsonFile
     * */
    @Override
    public String saveJsonFile(Configuration configuration){
        try {
            logger.info("Method called");
            String file = PropertyReader.getPropertyValue("json.file.path");
            assert file != null;
            String pathToJsonFile = new File(file).getAbsolutePath();
            FileWriter fileWriter = new FileWriter(pathToJsonFile);
            gson.toJson(configuration, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            logger.info("Details saved to configuration.json file");
            return "Successful";
        } catch (IOException e) {
            logger.error("An error occurred while saving json file" + e.getMessage());
            return "Unsuccessful";
        }
    }

    /**
     *  This method is used to read the configuration details from json file
     *
     *  @in  json File
     *  @Exception Exception
     *  @out Details in json file
     * */
    @Override
    public Configuration readJsonFile(){
        logger.info("Method called");
        Configuration configuration;
        try {
            String file = PropertyReader.getPropertyValue("json.file.path");
            assert file != null;
            FileReader fileReader = new FileReader(new File(file).getAbsolutePath());
            configuration = gson.fromJson(fileReader, Configuration.class);
            logger.info("Details read from configuration.json file");
            logger.info(configuration);
        }catch (Exception e){
            logger.error("An error occurred while reading json file" + e.getMessage());
            return null;
        }
        return configuration;
    }


    public String validateConfiguration(Configuration configuration, String variableName){
        logger.info("Method called");
        String resultMessage = null;

        switch (variableName){
            case "Total Tickets":
                resultMessage = (configuration.getTotalTickets() > 0) ? null : "Total Tickets should be greater than 0";
                break;
            case "Maximum Tickets":
                resultMessage = (configuration.getMaxTicketCapacity() > 0) ?
                        (configuration.getMaxTicketCapacity() < configuration.getTotalTickets()) ?
                                null : "Maximum Ticket Capacity should be less than Total Ticket Capacity."
                        : "Maximum Tickets should be greater than 0";
                break;
            case "Ticket Release Rate":
                resultMessage = (configuration.getTicketReleaseRate() > 0) ?
                        (configuration.getTicketReleaseRate() < configuration.getTotalTickets()) ?
                                (configuration.getTicketReleaseRate() < configuration.getMaxTicketCapacity()) ?
                                        null : "Ticket Release Rate should be less than Maximum Ticket Capacity."
                                : "Ticket Release Rate should be less than Total Ticket Capacity." :
                        "Ticket Release Rate should be greater than 0";
                break;
            case "Customer Rate":
                resultMessage = (configuration.getCustomerRetrievalRate() > 0) ?
                        (configuration.getCustomerRetrievalRate() < configuration.getTotalTickets()) ?
                                (configuration.getCustomerRetrievalRate() < configuration.getMaxTicketCapacity()) ?
                                        null : "Customer Retrieval Rate should be less than Maximum Ticket Capacity."
                                : "Customer Retrieval Rate should be less than Total Ticket Capacity." :
                        "Customer Retrieval Rate should be greater than 0";
                break;
        }

        return resultMessage;
    }

}
