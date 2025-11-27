package model;

import util.WeeksPattern;
import java.time.LocalTime;
enum SessionType {LECTURE, LAB, TUTORIAL};
/**
 * Represents a single scheduled session (lecture, lab, or tutorial).
 */
public class TimetableSlot {

    private String day;
    private LocalTime startTime, endTime;
    private int semester;
    private String weeks;
    private Room room;
    private Lecturer lecturer;
    private StudentGroup studentGroup;
    private Subgroup subgroup;
    private Module module;
    private SessionType type;

    public TimetableSlot(String day, LocalTime startTime, LocalTime endTime,
                         int semester, String weeks, Room room,
                         Lecturer lecturer, StudentGroup studentGroup,
                         Subgroup subgroup, Module module, SessionType type) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.semester = semester;
        this.weeks = weeks;
        this.room = room;
        this.lecturer = lecturer;
        this.studentGroup = studentGroup;
        this.subgroup = subgroup;
        this.module = module;
        this.type = type;
    }

    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Room getRoom() { return room; }
    public Lecturer getLecturer() { return lecturer; }
    public Module getModule() { return module; }
    public SessionType getType() { return type; }
    public int getSemester() { return semester; }
    public String getWeeks() { return weeks; }

    public boolean conflictsWith(TimetableSlot other) {
        if (!day.equalsIgnoreCase(other.day)) return false;
        if (semester != other.semester) return false;
        boolean timeOverlap = startTime.isBefore(other.endTime) &&
                other.startTime.isBefore(endTime);
        boolean weekOverlap = WeeksPattern.intersects(this.weeks, other.weeks);
        return timeOverlap && weekOverlap;
    }
}
