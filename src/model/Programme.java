package model;

/**
 * Represents an academic programme at the University of Limerick.
 */
public class Programme {

    /**
     * Returns the name of the programme.
     * @return programme name
     */
    public String getName() {
        return "";
    }

    /**
     * Returns the modules for a given year and semester.
     * @param year year of study
     * @param semester semester number
     * @return array of modules
     */
    public Module[] getModulesForSemester(int year, int semester) {
        return new Module[0];
    }
}
