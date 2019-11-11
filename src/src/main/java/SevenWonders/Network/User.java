package SevenWonders.Network;

// TODO: Change wonder to enum
public class User {
    public String username;
    public boolean isAdmin;
    public boolean isReady;
    public String selectedWonder;

    public User(String username) {
        this.username = username;
        this.isAdmin = false;
        this.isReady = false;
        this.selectedWonder = "";
    }
}
