package model;

/**
 * Represents a group of students belonging to the same programme and year.
 */
public class StudentGroup {

    /**
     * Returns the ID/name of the student group.
     * @return group name
     */
    public String getGroupName() {
        return "";
    }

    /**
     * Returns the subgroups belonging to this group.
     * @return array of subgroups
     */
    public Subgroup[] getSubgroups() {
        return new Subgroup[0];
    }
}
