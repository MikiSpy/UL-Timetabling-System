package model;

/**
 * Represents a lecturer who teaches modules.
 */
public class Lecturer extends User {
    private Module[] modules;

    /**
     * Returns the list of modules this lecturer teaches.
     * @return an array of modules
     */
    public Module[] getModules() {
        return modules;
    }
}
