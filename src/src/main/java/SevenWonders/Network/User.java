package SevenWonders.Network;

// TODO: Change wonder to enum
class User {

    private String username;
    private String selectedWonder;

    private boolean isAdmin;
    private boolean isReady;


    public User(String username) {
        this.username = username;
        this.isAdmin = false;
        this.isReady = false;
        this.selectedWonder = "";
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getSelectedWonder() {
        return selectedWonder;
    }

    public void setSelectedWonder(String selectedWonder) {
        this.selectedWonder = selectedWonder;
    }
}
