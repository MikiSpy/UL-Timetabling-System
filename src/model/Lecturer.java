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

    /**
     * Create a lecturer.
     * @param id lecturer ID
     * @param name lecturer name
     * @param email lecturer email
     * @param password lecturer password
     */
    public Lecturer(String id, String name, String email, String password) {
        super(name, password);
        this.name = name;
        this.modules = new Module[0];
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Set modules taught by this lecturer.
     * @param modules array of modules
     */
    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    /**
     * Update lecturer ID.
     * @param id new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get lecturer ID.
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get modules taught by this lecturer.
     * @return array of modules
     */
    public Module[] getModules() {
        return modules;
    }

    /**
     * Lecturers do not have admin access.
     * @return false
     */
    @Override
    public boolean hasAdminAccess() {
        return false;
    }

    /**
     * Get lecturer name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get lecturer email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get lecturer password.
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
