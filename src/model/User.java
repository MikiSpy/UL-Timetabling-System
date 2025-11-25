package model;

/**
 * Represents a generic user in the system.
 */
public abstract class User {
    protected String name;
    protected String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    /**
     * Returns the username for this user.
     * @return the username
     */
    public String getUsername() {
        return name;
    }

    /**
     * Returns the password for this user.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public abstract boolean hasAdminAccess();
}
