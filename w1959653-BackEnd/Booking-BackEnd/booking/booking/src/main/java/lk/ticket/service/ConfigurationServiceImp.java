package lk.ticket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.ticket.model.Configuration;
import lk.ticket.repository.ConfigurationRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

@Service
public class ConfigurationServiceImp implements ConfigarationService{
    private static final Logger logger = Logger.getLogger(ConfigurationServiceImp.class);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImp(ConfigurationRepository configurationRepository){
        this.configurationRepository = configurationRepository;
    }

    /**
     *  This method is used to submit the configuration details
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @throws Exception
     *  @out resultMessage
     * */

    @Override
    public String submitConfiguration(Configuration configuration, String user){
        logger.info("Method called");
        LocalDate localDate = LocalDate.now();
//        String result = configurationRepository.submitConfiguration(configuration, user, localDate.toString());
        return saveJsonFile(configuration);
    }

    /**
     *  This method is used to save the configuration details to json file
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @throws Exception
     *  @out jsonFile
     * */
    @Override
    public String saveJsonFile(Configuration configuration){
        try {
            logger.info("Method called");
            String pathToJsonFile = new File("booking/booking/src/json/configuration.json").getAbsolutePath();
            FileWriter fileWriter = new FileWriter(pathToJsonFile);
            gson.toJson(configuration, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            logger.info("Details saved to configuration.json file");
            return "Successful";
        } catch (Exception e) {
            logger.error("An error occurred while saving json file" + e.getMessage());
            e.printStackTrace();
            return "Unsuccessful";
        }
    }

    /**
     *  This method is used to read the configuration details from json file
     *
     *  @in  json File
     *  @throws Exception
     *  @out Details in json format
     * */
    @Override
    public Configuration readJsonFile(){
        logger.info("Method called");
        Configuration configuration;
        try {
            FileReader fileReader = new FileReader(new File("booking/booking/src/json/configuration.json").getAbsolutePath());
            configuration = gson.fromJson(fileReader, Configuration.class);
            logger.info("Details read from configuration.json file");
            logger.info(configuration);
        }catch (Exception e){
            logger.error("An error occurred while reading json file" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return configuration;
    }

}
