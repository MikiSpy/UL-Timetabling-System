package model;

/**
 * Represents a group of students belonging to the same programme and year.
 */
public class StudentGroup {
    private String id;
    private Subgroup[] subgroups;


    public StudentGroup(String id, Subgroup[] subgroups) {
        this.id = id;
        this.subgroups = subgroups;
    }

    public StudentGroup(String id) {
        this.id = id;
        this.subgroups = new Subgroup[0]; // empty array by default
    }

    /**
     * Returns the ID/name of the student group.
     * @return group name
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the subgroups belonging to this group.
     * @return array of subgroups
     */
    public Subgroup[] getSubgroups() {
        return subgroups;
    }
}
