package model;

/**
 * Represents a student enrolled in a UL programme.
 */
public class Student extends User {
    private String name, password, id, email;
    private Programme programme;
    private int year;

    public Student(String name, String password, String id, String email){
        super(name, password);
        this.id = id;
        this.email = email;
    }


    public Student(String name, String password, String id, Programme programme, int year) {
        super(name, password);
        this.id = id;
        this.programme = programme;
        this.year = year;
    }

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

    @Override
    public boolean hasAdminAccess() {
        return false;
    }
}
