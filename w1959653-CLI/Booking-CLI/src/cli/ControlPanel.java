package cli;

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

                //Create and start vendor Thread
                Vendor vendor = new Vendor(ticketPool);
                Thread vendorThread = new Thread(vendor);
                vendorThread.start();

                //Create and start customer Thread
                Customer customer = new Customer(ticketPool);
                Thread customerThread = new Thread(customer);
                customerThread.start();

                //waiting for a vendor and customer threads to terminate
                try {
                    vendorThread.join();
                    customerThread.join();
                } catch (InterruptedException e) {
                    Logger.error(methodDetails + " An error occurred while interrupting the vendor thread " + Thread.currentThread().getName() + " : " + e.getMessage());
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
