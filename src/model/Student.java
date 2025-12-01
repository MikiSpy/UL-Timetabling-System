package model;

/**
 * Represents a student enrolled in a programme.
 */
public class Student extends User {
    private String name, password, id, email;
    private Programme programme;
    private int year;
    private String studentGroupId;
    private StudentGroup studentGroup;

    /**
     * Create a student with basic details.
     * @param name student name
     * @param password student password
     * @param id student ID
     * @param email student email
     */
    public Student(String name, String password, String id, String email) {
        super(name, password);
        this.name = name;
        this.id = id;
        this.email = email;
    }

    /**
     * Create a student with programme and group details.
     * @param name student name
     * @param password student password
     * @param id student ID
     * @param programme programme enrolled in
     * @param year year of study
     * @param studentGroupId student group ID
     */
    public Student(String name, String password, String id, Programme programme, int year, String studentGroupId) {
        super(name, password);
        this.name = name;
        this.id = id;
        this.programme = programme;
        this.year = year;
        this.studentGroupId = studentGroupId;
    }

    /**
     * Get student group ID.
     * @return group ID
     */
    public String getStudentGroupId() {
        return studentGroupId;
    }

    /**
     * Get student group object.
     * @return student group
     */
    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }

    /**
     * Set student group ID.
     * @param id new group ID
     */
    public void setStudentGroupId(String id) {
        this.studentGroupId = id;
    }

    /**
     * Get programme.
     * @return programme
     */
    public Programme getProgramme() {
        return programme;
    }

    /**
     * Get year of study.
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Get student name.
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get student password.
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Students do not have admin access.
     * @return false
     */
    @Override
    public boolean hasAdminAccess() {
        return false;
    }
}
