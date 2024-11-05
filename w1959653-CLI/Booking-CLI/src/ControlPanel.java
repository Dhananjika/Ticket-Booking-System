import java.util.Scanner;

public class ControlPanel {
    Configuration configuration = new Configuration();

    public void selectOption(){
        if (configuration.getTotalTickets() == 0){
            System.out.println();
            System.out.println("Configuration Form : ");
            executeOption("y");
        }else{
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("1 - Start the System");
            System.out.println("2 - Stop the System");
            System.out.println();
            System.out.print("Enter option: ");
            String option = scanner.nextLine();
            executeOption(option);
        }

    }

    public void executeOption(String option){
        String methodDetails = "[ControlPanel] -- executeOption : ";
        switch (option){
            case "y":
                configuration.setConfiguration();
                break;
            case "1":
                TicketPool ticketPool = new TicketPool(configuration);
                Vendor vendor = new Vendor(ticketPool);
                Customer customer = new Customer(ticketPool);

                Thread vendorThread = new Thread(vendor);
                vendorThread.start();

                Thread customerThread = new Thread(customer);
                customerThread.start();

                try {
                    vendorThread.join();
                    customerThread.join();
                } catch (InterruptedException e) {
                    Logger.error(methodDetails + " An error occurred while interrupting the vendor thread " + Thread.currentThread().getName() + " : " + e.getMessage());
                }
                break;
            case "2":
                configuration.resetConfiguration();
                Logger.info(methodDetails + "System Stopped");
                break;
            default:
                Logger.error(methodDetails + "Invalid option!");
                System.out.println();
                break;
        }

        if(!option.equals("2")){
            selectOption();
        }
    }
}
