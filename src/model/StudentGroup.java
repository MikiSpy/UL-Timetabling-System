package model;

/**
 * Represents a group of students belonging to the same programme and year.
 */
public class StudentGroup {
    private String id;
    private Subgroup[] subgroups;

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
