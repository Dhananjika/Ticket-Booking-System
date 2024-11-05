package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TicketSystemCLI {

    public static void main(String[] args) {
        String methodDetails = "[TicketingSystem] -- main : ";
        try {
            FileReader fileReader = new FileReader(new File("src/Logo.txt").getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                System.out.println(bufferedReader.readLine());
            }
            bufferedReader.close();

            TicketSystemCLI cli = new TicketSystemCLI();
            cli.logginUser();
        } catch (IOException e) {
            Logger.error(methodDetails + "Login file not found");
        }
    }

    public void logginUser() {
        String methodDetails = "[TicketingSystem] -- logginUser : ";
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
            controlPanel.selectOption();
        }else{
            Logger.warn(methodDetails + "Invalid username or password!");
            logginUser();
        }
    }

}