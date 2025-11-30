package model;
import model.*;
/**
 * Represents a student enrolled in a UL programme.
 */
public class Student extends User {
    private String name, password, id, email;
    private Programme programme;
    private int year;
    private String studentGroupId;
    private StudentGroup studentGroup;

    public Student(String name, String password, String id, String email){
        super(name, password);
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public Student(String name, String password, String id, Programme programme, int year, String studentGroupId) {
        super(name, password);
        this.name = name;
        this.id = id;
        this.programme = programme;
        this.year = year;
        this.studentGroupId = studentGroupId;
    }

    public String getStudentGroupId() {
        return studentGroupId;
    }
    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }
    public void setStudentGroupId(String id) {
        this.studentGroupId = id;
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
    public String getName(){
        return name;
    }
    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public boolean hasAdminAccess() {
        return false;
    }
}
