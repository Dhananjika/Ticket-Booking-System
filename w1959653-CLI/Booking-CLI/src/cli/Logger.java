package cli;

public class Logger {
    public static void info(String msg) {
        System.out.println("[Log] -- [INFO] --- " + msg);
    }

    public static void error(String msg) {
        System.out.println("[Log] -- [ERROR] --- " + msg);
    }

    public static void warn(String msg) {
        System.out.println("[Log] -- [WARN] --- " + msg);
    }
}
