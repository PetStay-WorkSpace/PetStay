package model;

/**
 * Simple application session holder to keep the current logged/registered Proprietario
 */
public class Session {
    private static Session instance;
    private Proprietario user;

    private Session() {
    }

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Proprietario getUser() {
        return user;
    }

    public void setUser(Proprietario user) {
        this.user = user;
    }

    public void clear() {
        this.user = null;
    }
}
