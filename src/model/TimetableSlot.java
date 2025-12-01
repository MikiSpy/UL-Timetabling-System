package model;

import util.WeeksPattern;
import java.time.LocalTime;

/**
 * Represents a scheduled session in the timetable (lecture, lab, tutorial).
 */
public class TimetableSlot {

    private String day;
    private LocalTime startTime, endTime;
    private int semester;
    private String weeks;
    private Room room;
    private Lecturer lecturer;
    private String studentGroupId;
    private Subgroup subgroup;
    private Module module;
    private SessionType type;
    private StudentGroup studentGroup;

    /**
     * Create a timetable slot.
     * @param day day of session
     * @param startTime start time
     * @param endTime end time
     * @param semester semester number
     * @param weeks weeks pattern
     * @param room room
     * @param lecturer lecturer
     * @param studentGroupId student group ID
     * @param subgroup subgroup
     * @param module module
     * @param type session type
     */
    public TimetableSlot(String day, LocalTime startTime, LocalTime endTime, int semester, String weeks,
                         Room room, Lecturer lecturer, String studentGroupId, Subgroup subgroup,
                         Module module, SessionType type) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.semester = semester;
        this.weeks = weeks;
        this.room = room;
        this.lecturer = lecturer;
        this.studentGroupId = studentGroupId;
        this.subgroup = subgroup;
        this.module = module;
        this.type = type;
    }

    /** @return day */
    public String getDay() { return day; }

    /** @return start time */
    public LocalTime getStartTime() { return startTime; }

    /** @return end time */
    public LocalTime getEndTime() { return endTime; }

    /** @return room */
    public Room getRoom() { return room; }

    /** @return lecturer */
    public Lecturer getLecturer() { return lecturer; }

    /** @return module */
    public Module getModule() { return module; }

    /** @return session type */
    public SessionType getType() { return type; }

    /** @return semester */
    public int getSemester() { return semester; }

    /** @return weeks pattern */
    public String getWeeks() { return weeks; }

    /** @return subgroup */
    public Subgroup getSubgroup() { return subgroup; }

    /** @return subgroup ID or null */
    public String getSubgroupId() {
        return subgroup != null ? subgroup.getId() : null;
    }

    /** Set day. */
    public void setDay(String day) { this.day = day; }

    /** Set start time. */
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    /** Set end time. */
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    /** Set semester. */
    public void setSemester(int semester) { this.semester = semester; }

    /** Set weeks pattern. */
    public void setWeeks(String weeks) { this.weeks = weeks; }

    /** Set room. */
    public void setRoom(Room room) { this.room = room; }

    /** Set lecturer. */
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }

    /** Set module. */
    public void setModule(Module module) { this.module = module; }

    /** Set session type. */
    public void setType(SessionType type) { this.type = type; }

    /** Set student group ID. */
    public void setStudentGroupId(String studentGroupId) { this.studentGroupId = studentGroupId; }

    /** Set subgroup. */
    public void setSubgroup(Subgroup subgroup) { this.subgroup = subgroup; }

    /**
     * Check if this slot conflicts with another.
     * @param other other slot
     * @return true if conflict
     */
    public boolean conflictsWith(TimetableSlot other) {
        if (!day.equalsIgnoreCase(other.day)) return false;
        if (semester != other.semester) return false;
        boolean timeOverlap = startTime.isBefore(other.endTime) &&
                other.startTime.isBefore(endTime);
        boolean weekOverlap = WeeksPattern.intersects(this.weeks, other.weeks);
        return timeOverlap && weekOverlap;
    }

    /** @return student group ID */
    public String getStudentGroupId() { return studentGroupId; }

    /** @return student group */
    public StudentGroup getStudentGroup() { return studentGroup; }
}
