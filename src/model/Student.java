package model;

/**
 * Represents a student enrolled in a UL programme.
 */
public class Student extends User {
    private String id;
    private Programme programme;
    private int year;

    /**
     * Returns the programme the student is enrolled in.
     * @return the programme
     */
    public Programme getProgramme() {
        return programme;
    }

    /**
     * Returns the student's year of study.
     * @return the year
     */
    public int getYear() {
        return year;
    }
}
