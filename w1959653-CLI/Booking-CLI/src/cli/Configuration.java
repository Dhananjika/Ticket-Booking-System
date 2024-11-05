package cli;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

public class Configuration {

    // Instance variables
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    /**
     *  This method is used to display the Configuration variable values
     * */
    @Override
    public String toString() {
        return "Configuration[Total Number of Tickets - " + totalTickets + ", Ticket Release Rate - " + ticketReleaseRate + ", Customer Retrieval Rate - " + customerRetrievalRate + ", Maximum Ticket Capacity - " + maxTicketCapacity + "]" ;
    }

    public void setConfiguration() {
        String methodDetails = "[Configuration] -- [setConfiguration] : ";
        try {
            resetConfiguration();

            totalTickets = setVariableValues("Total Number of Tickets");
            maxTicketCapacity = setVariableValues("Maximum Ticket Capacity");
            ticketReleaseRate = setVariableValues("Ticket Release Rate");
            customerRetrievalRate = setVariableValues("Customer Retrieval Rate");

            Logger.info(methodDetails + "Setting configuration completed.");
            System.out.println();


        } catch (InputMismatchException e) {
            Logger.error(methodDetails + "Positive number expected" + e.getMessage());
            System.out.println();
            setConfiguration();
        }
    }

    /**
     *  This method is used to return variable value
     *
     *  @in  variable Name
     *  @out variable Value
     * */
    private int setVariableValues(String variableName) {
        String methodDetails = "[Configuration] -- [setVariableValues] : ";
        Scanner scanner = new Scanner(System.in);
        int variableValue = 0;

        switch (variableName) {
            case "Total Number of Tickets":
                while (variableValue <= 0){
                    System.out.print("Enter Total Number of Tickets : " );
                    variableValue = scanner.nextInt();

                    if (variableValue <= 0) {
                        Logger.warn(methodDetails + "Total Number Of Tickets should be grater than 0.");
                        System.out.println();
                    }
                }
                break;
            case "Maximum Ticket Capacity":
                while (variableValue <= 0){
                    System.out.print("Enter Maximum Ticket Capacity : " );
                    variableValue = scanner.nextInt();

                    if (variableValue <= 0) {
                        Logger.warn(methodDetails + "Maximum Ticket Capacity should be grater than 0.");
                        System.out.println();
                    } else if (variableValue > totalTickets) {
                        Logger.warn(methodDetails + "Maximum Ticket Capacity should be less than Total Number Of Tickets.");
                        variableValue = 0;
                        System.out.println();
                    }
                }
                break;
            default:
                while (variableValue <= 0){
                    System.out.print("Enter "+ variableName+" : " );
                    variableValue = scanner.nextInt();

                    if (variableValue <= 0) {
                        Logger.warn(methodDetails + variableName + " should be grater than 0.");
                        System.out.println();
                    }else if (variableValue > totalTickets) {
                        Logger.warn(methodDetails + variableName + " should be less than Total Number Of Tickets.");
                        variableValue = 0;
                        System.out.println();
                    } else if (variableValue > maxTicketCapacity) {
                        Logger.warn(methodDetails + variableName + " should be less than Maximum Ticket Capacity.");
                        variableValue = 0;
                        System.out.println();
                    }
                }
                break;
        }
        return variableValue;
    }

    /**
     *  This method is used to reset configuration values
     * */
    public void resetConfiguration() {
        totalTickets = 0;
        ticketReleaseRate = 0;
        customerRetrievalRate = 0;
        maxTicketCapacity = 0;
    }


    // Getters Setters of variables
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
