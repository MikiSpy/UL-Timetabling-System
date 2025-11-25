package model;

/**
 * Represents a lecturer who teaches modules.
 */
public class Lecturer extends User {
    private Module[] modules;
    private String name;
    private String password;
    private String id;
    private String email;

    public Lecturer(String name, String password, String id, String email) {
        super(name, password);
        this.modules = new Module[0];
        this.id = id;
        this.email = email;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    /**
     * Returns the list of modules this lecturer teaches.
     * @return an array of modules
     */
    public Module[] getModules() {
        return modules;
    }

    @Override
    public boolean hasAdminAccess() {
        return false;
    }
}
