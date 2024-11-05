package cli;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

//Abstract class
public abstract class Login {
    protected String username;
    protected String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *  This method is used to sign in verification.
     *  This is an abstract method.
     * */
    public abstract boolean login();
}
