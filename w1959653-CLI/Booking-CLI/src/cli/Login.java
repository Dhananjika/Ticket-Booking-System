package cli;

public abstract class Login {
    protected String username;
    protected String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract boolean login();
}
