package model;

/**
 * Represents a subgroup (e.g., lab/tutorial split).
 */
public class Subgroup {
    private String id;
    private StudentGroup parentGroup;

    /**
     * Constructs a Subgroup with an ID and its parent StudentGroup.
     * @param id the subgroup ID or name
     * @param parentGroup the parent StudentGroup
     */
    public Subgroup(String id, StudentGroup parentGroup) {
        this.id = id;
        this.parentGroup = parentGroup;
    }

    /**
     * Returns the subgroup ID or name.
     * @return subgroup ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the parent StudentGroup.
     * @return parent group
     */
    public StudentGroup getParentGroup() {
        return parentGroup;
    }
}
