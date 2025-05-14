package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This system consider only one event
 * <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

public class TicketSystemCLI {

    /**
     *  This method is used to read and print pattern of logo file and call the LoginUser method
     *
     *  @in  logo text file
     *  @Exception IOException
     *  @out print file pattern
     * */
    public static void main(String[] args) {
        String methodDetails = "[TicketingSystem] -- [main] : ";
        try {
            FileReader fileReader = new FileReader(new File("Booking-CLI/src/Logo.txt").getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                System.out.println(bufferedReader.readLine());
            }
            bufferedReader.close();

            TicketSystemCLI cli = new TicketSystemCLI();
            cli.loginUser();
        } catch (IOException e) {
            Logger.error(methodDetails + "Login file not found " + e.getMessage());
        }
    }

    /**
     *  This method is used to call validation of login credential method and call the panel
     *
     *  @in  username, password
     *  @out login to the System
     * */
    public void loginUser() {
        String methodDetails = "[TicketingSystem] -- [loginUser] : ";
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        Login adminLogin = new AdminLogin(username, password);

        if(adminLogin.login()) {
            Logger.info(methodDetails + "You have successfully logged in as Administrator!");
            ControlPanel controlPanel = new ControlPanel();
            controlPanel.displayPanel();
        }else{
            Logger.warn(methodDetails + "Invalid username or password!");
            loginUser();
        }
    }

}