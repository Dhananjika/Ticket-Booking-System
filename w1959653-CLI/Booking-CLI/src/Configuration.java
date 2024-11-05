import java.util.InputMismatchException;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    @Override
    public String toString() {
        return "Configuration[Total Number of Tickets - " + totalTickets + ", Ticket Release Rate - " + ticketReleaseRate + ", Customer Retrieval Rate - " + customerRetrievalRate + ", Maximum Ticket Capacity - " + maxTicketCapacity + "]" ;
    }

    public void setConfiguration() {
        String methodDetails = "[Configuration] -- setConfiguration : ";
        try {
            resetConfiguration();

            Scanner scanner = new Scanner(System.in);
            while (totalTickets <= 0){
                System.out.print("Enter Total Number of Tickets : " );
                totalTickets = scanner.nextInt();

                if (totalTickets <= 0) {
                    Logger.warn(methodDetails + "Total Number Of Tickets should be grater than 0.");
                    System.out.println();
                }
            }

            while (maxTicketCapacity <= 0){
                System.out.print("Enter Maximum Ticket Capacity : " );
                maxTicketCapacity = scanner.nextInt();

                if (maxTicketCapacity <= 0) {
                    Logger.warn(methodDetails + "Maximum Ticket Capacity should be grater than 0.");
                    System.out.println();
                } else if (maxTicketCapacity > totalTickets) {
                    Logger.warn(methodDetails + "Maximum Ticket Capacity should be less than Total Number Of Tickets.");
                    System.out.println();
                }
            }

            while (ticketReleaseRate <= 0){
                System.out.print("Enter Ticket Release Rate : " );
                ticketReleaseRate = scanner.nextInt();

                if (ticketReleaseRate <= 0) {
                    Logger.warn(methodDetails + "Ticket Release Rate should be grater than 0.");
                    System.out.println();
                }else if (ticketReleaseRate > totalTickets) {
                    Logger.warn(methodDetails + "Ticket Release Rate should be less than Total Number Of Tickets.");
                    System.out.println();
                } else if (ticketReleaseRate > maxTicketCapacity) {
                    Logger.warn(methodDetails + "Ticket Release Rate should be less than Maximum Ticket Capacity.");
                    System.out.println();
                }
            }

            while (customerRetrievalRate <= 0){
                System.out.print("Enter Customer Retrieval Rate : " );
                customerRetrievalRate = scanner.nextInt();

                if (customerRetrievalRate <= 0) {
                    Logger.warn(methodDetails + "Customer Retrieval Rate should be grater than 0.");
                    System.out.println();
                }else if (customerRetrievalRate > totalTickets) {
                    Logger.warn(methodDetails + "Customer Retrieval Rate should be less than Total Number Of Tickets.");
                    System.out.println();
                } else if (customerRetrievalRate > maxTicketCapacity) {
                    Logger.warn(methodDetails + "Customer Retrieval Rate should be less than Maximum Ticket Capacity.");
                    System.out.println();
                }
            }
            Logger.info(methodDetails + "Setting configuration completed.");
            System.out.println();


        } catch (InputMismatchException e) {
            Logger.error(methodDetails + "Positive number expected" + e.getMessage());
            System.out.println();
            setConfiguration();
        }
    }


    public void resetConfiguration() {
        totalTickets = 0;
        ticketReleaseRate = 0;
        customerRetrievalRate = 0;
        maxTicketCapacity = 0;
    }


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
