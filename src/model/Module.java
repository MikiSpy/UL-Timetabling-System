package model;

/**
 * Represents an academic module.
 */
public class Module {
    private String code, title;
    private int lectureHours, labHours, tutorialHours, year, semester;

    Module(String code, String title, int lectureHours, int labHours, int tutorialHours, int year, int semester) {
        this.code = code;
        this.title = title;
        this.lectureHours = lectureHours;
        this.labHours = labHours;
        this.tutorialHours = tutorialHours;
        this.year = year;
        this.semester = semester;
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
}
