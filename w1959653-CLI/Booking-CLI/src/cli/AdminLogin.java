package cli;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

// Admin class inherited by Login abstract class
public class AdminLogin extends Login{

    //Parameterized Constructor
    public AdminLogin(String username, String password) {
        super(username, password);
    }

    /**
     *  This method is used to sign in verification
     *
     *  @in  username and password
     *  @out username password is correct or not
     * */
    @Override
    public boolean login() {
        return (super.username.equals("admin") && super.password.equals("admin"));
    }
}
