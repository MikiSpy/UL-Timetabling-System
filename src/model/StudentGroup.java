package model;

/**
 * Represents a group of students in a programme and year.
 */
public class StudentGroup {
    private String id;
    private Subgroup[] subgroups;

    /**
     * Create a student group with subgroups.
     * @param id group ID
     * @param subgroups array of subgroups
     */
    public StudentGroup(String id, Subgroup[] subgroups) {
        this.id = id;
        this.subgroups = subgroups;
    }

    /**
     * Create a student group with no subgroups.
     * @param id group ID
     */
    public StudentGroup(String id) {
        this.id = id;
        this.subgroups = new Subgroup[0];
    }

    /**
     * Get group ID.
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get subgroups.
     * @return array of subgroups
     */
    public Subgroup[] getSubgroups() {
        return subgroups;
    }

    /**
     * Set group ID.
     * @param newValue new ID
     */
    public void setId(String newValue) {
        this.id = newValue;
    }
}
