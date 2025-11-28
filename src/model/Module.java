package model;

/**
 * Represents an academic module.
 */
public class Module {
    private String code, title, weeks, programmeCode;
    private int lectureHours, labHours, tutorialHours, year, semester;

    public Module(String code, String title, int lectureHours, int labHours, int tutorialHours, int year, int semester, String weeks, String programmeCode) {
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
     * Returns the module code.
     * @return module code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the number of lecture hours.
     * @return lecture hours
     */
    public int getLectureHours() {
        return lectureHours;
    }

    /**
     * Returns the number of lab hours.
     * @return lab hours
     */
    public int getLabHours() {
        return labHours;
    }

    /**
     * Returns the number of tutorial hours.
     * @return tutorial hours
     */
    public int getTutorialHours() {
        return tutorialHours;
    }

    public String getTitle() {
        return title;
    }

    public String getWeeks() {
        return weeks;
    }

    public String getProgrammeCode(){
        return programmeCode;
    }


}
