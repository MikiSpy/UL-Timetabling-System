package model;

/**
 * Represents a generic user in the system.
 */
public abstract class User {
    private String name;
    private String password;
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
}
