package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

public class ControlPanel {
    //Created instance object for Configuration class
    Configuration configuration = new Configuration();
    boolean ticketsSoldOut = false;

    /**
     *  This method is used to display the panel
     *
     *  @in  option value
     *  @out execution of that value
     * */
    public void displayPanel(){
        String methodDetails = "[ControlPanel] -- [displayPanel] : ";
        Scanner scanner = new Scanner(System.in);
        if (configuration.getTotalTickets() == 0){
            // Before set configuration values this will execute
            System.out.println();
            System.out.println("Configuration Form : ");
            executeOption("y");
        } else if (ticketsSoldOut) {
            System.out.println();
            System.out.println("Do you want to stop the system ? (yes/no)");
            System.out.println("Note : Selecting \"yes\" will terminate the system.");
            System.out.println("       Selecting \"no\" requires configuring configuration settings for a different event.");
            System.out.println();
            System.out.print("Enter option: ");
            String option = scanner.nextLine();

            if (option.equals("yes")){
                executeOption("2");
            }else if (option.equals("no")){
                ticketsSoldOut = false;
                executeOption("y");
            }else {
                Logger.warn(methodDetails + "Invalid option selected.");
            }

        } else{
            // After set configuration values this will execute
            System.out.println();
            System.out.println("1 - Start the System");
            System.out.println("2 - Stop the System");
            System.out.println();
            System.out.print("Enter option: ");
            String option = scanner.nextLine();
            executeOption(option);
        }

    }

    /**
     *  This method is used to execute the input option block methods
     *
     *  @in  option value
     *  @Exception InterruptedException
     *  @out execution of that value
     * */
    public void executeOption(String option){
        String methodDetails = "[ControlPanel] -- [executeOption] : ";
        switch (option){
            case "y":
                //For set configuration values
                configuration.setConfiguration();
                break;
            case "1":
                //Start the system
                //Create TicketPool class object by parsing configuration object.
                TicketPool ticketPool = new TicketPool(configuration);

                List<Thread> vendorThreads = new ArrayList<>();
                List<Thread> customerThreads = new ArrayList<>();

                // Create and start vendor threads
                for (int i = 0; i < 5; i++) {
                    Vendor vendor = new Vendor(ticketPool);
                    Thread vendorThread = new Thread(vendor, "Vendor" + (i + 1));
                    vendorThreads.add(vendorThread);
                    vendorThread.start();
                }

                // Create and start customer threads
                for (int j = 0; j < 6; j++) {
                    Customer customer = new Customer(ticketPool);
                    Thread customerThread = new Thread(customer, "Customer" + (j + 1));
                    customerThreads.add(customerThread);
                    customerThread.start();
                }

                // Waiting for all vendor and customer threads to terminate
                try {
                    for (Thread vendorThread : vendorThreads) {
                        vendorThread.join();
                    }
                    for (Thread customerThread : customerThreads) {
                        customerThread.join();
                    }
                } catch (InterruptedException e) {
                    Logger.error(methodDetails + " An error occurred while waiting for threads to finish: " + e.getMessage());
                }

                ticketsSoldOut = true;
                break;
            case "2":
                //Stop the system
                configuration.resetConfiguration();
                Logger.info(methodDetails + "System Stopped");
                break;
            default:
                Logger.error(methodDetails + "Invalid option!");
                System.out.println();
                break;
        }

        //If system stopped then this will not execute
        if(!option.equals("2")){
            displayPanel();
        }
    }
}
