package model;

/**
 * Represents an admin user who can modify timetables.
 */
public class Admin extends User {

    /**
     * Returns true since admin users have full system access.
     * @return access level confirmation
     */
    public boolean hasAdminAccess() {
        return true;
    }
}
