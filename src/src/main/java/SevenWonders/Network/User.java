package SevenWonders.Network;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;

class User {

    private String username;

    private int id;
    private WONDER_TYPE selectedWonder;

    private boolean isAdmin;
    private boolean isReady;


    public User(String username) {
        this.username = username;
        this.isAdmin = false;
        this.isReady = false;
        this.selectedWonder = WONDER_TYPE.COLOSSUS_OF_RHODES;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public WONDER_TYPE getSelectedWonder() {
        return selectedWonder;
    }

    public void setSelectedWonder(WONDER_TYPE selectedWonder) {
        this.selectedWonder = selectedWonder;
    }
}
