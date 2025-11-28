package model;

/**
 * Represents a generic user in the system.
 */
public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String email;

    public User(String name, String password) {
        this.id = name;
        this.password = password;
    }
    /**
     * Returns the username for this user.
     * @return the username
     */
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
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
