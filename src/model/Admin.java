package model;

/**
 * Represents an admin user who can modify timetables.
 */
public class Admin extends User {

    public Admin(String name, String password) {
        super(name, password);
    }
    /**
     * Returns true since admin users have full system access.
     * @return access level confirmation
     */
    @Override
    public boolean hasAdminAccess() {
        return true;
    }

}
