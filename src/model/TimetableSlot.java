package model;

import javax.security.auth.Subject;

/**
 * Represents a single scheduled session (lecture, lab, or tutorial).
 */
public class TimetableSlot {

    private String day;
    private String startTime, endTime;
    private int semester;
    private String weeks;
    private Room room;
    private Lecturer lecturer;
    private StudentGroup studentGroup;
    private Subgroup subgroup;
    private Module module;

    TimetableSlot(String day, String startTime, String endTime, int semester, String weeks, Room room, Lecturer lecturer, StudentGroup studentGroup, Subgroup subgroup, Module module) {
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
    }

    /**
     * Returns the day of the week for this slot.
     * @return day index
     */
    public String getDay() {
        return day;
    }

    /**
     * Returns the start time of the session.
     * @return start time as a string
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the room used for this session.
     * @return the room
     */
    public Room getRoom() {
        return room;
    }
}
