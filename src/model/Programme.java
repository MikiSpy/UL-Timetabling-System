package model;

import java.util.Arrays;

/**
 * Represents an academic programme at the University of Limerick.
 */
public class Programme {
    private String name;
    private String code;
    private Module[] modules;

    public Programme(String name, String code, Module[] modules) {
        this.name = name;
        this.code = code;
        this.modules = modules;
    }

    /**
     * Returns the name of the programme.
     * @return programme name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the programme code.
     * @return programme code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns all modules in the programme.
     * @return array of modules
     */
    public Module[] getAllModules() {
        return modules;
    }

    /**
     * Returns the modules for a given year and semester.
     * @param year year of study
     * @param semester semester number
     * @return array of modules
     */
    public Module[] getModulesForSemester(int year, int semester) {
        return modules;
    }
}
