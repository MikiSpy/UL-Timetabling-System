package model;

/**
 * Represents an academic module.
 */
public class Module {
    private String code, title, weeks, programmeCode;
    private int lectureHours, labHours, tutorialHours, year, semester;

    /**
     * Create a module with full details.
     * @param code module code
     * @param title module title
     * @param lectureHours lecture hours
     * @param labHours lab hours
     * @param tutorialHours tutorial hours
     * @param year year of study
     * @param semester semester number
     * @param weeks weeks pattern
     * @param programmeCode programme code
     */
    public Module(String code, String title, int lectureHours, int labHours, int tutorialHours,
                  int year, int semester, String weeks, String programmeCode) {
        this.code = code;
        this.title = title;
        this.lectureHours = lectureHours;
        this.labHours = labHours;
        this.tutorialHours = tutorialHours;
        this.year = year;
        this.semester = semester;
        this.weeks = weeks;
        this.programmeCode = programmeCode;
    }

    /**
     * Create a module with only a code.
     * @param code module code
     */
    public Module(String code) {
        this.code = code;
    }

    /**
     * Get module code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get lecture hours.
     * @return lecture hours
     */
    public int getLectureHours() {
        return lectureHours;
    }

    /**
     * Get lab hours.
     * @return lab hours
     */
    public int getLabHours() {
        return labHours;
    }

    /**
     * Get tutorial hours.
     * @return tutorial hours
     */
    public int getTutorialHours() {
        return tutorialHours;
    }

    /**
     * Get module title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get weeks pattern.
     * @return weeks
     */
    public String getWeeks() {
        return weeks;
    }

    /**
     * Get programme code.
     * @return programme code
     */
    public String getProgrammeCode() {
        return programmeCode;
    }
}
