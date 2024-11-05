package cli;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

//Log print class
public class Logger {

    //INFO log static method
    public static void info(String msg) {
        System.out.println("[Log] -- [INFO] --- " + msg);
    }

    //ERROR log static method
    public static void error(String msg) {
        System.out.println("[Log] -- [ERROR] --- " + msg);
    }

    //WARN log static method
    public static void warn(String msg) {
        System.out.println("[Log] -- [WARN] --- " + msg);
    }
}
