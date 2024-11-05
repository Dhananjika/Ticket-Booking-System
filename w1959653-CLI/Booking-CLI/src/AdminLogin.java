public class AdminLogin extends Login{
    public AdminLogin(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean login() {
        return (super.username.equals("admin") && super.password.equals("admin"));
    }
}
