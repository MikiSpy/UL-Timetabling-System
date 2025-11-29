package repository;

import model.Timetable;
import model.TimetableSlot;


public interface TimetableRepository {
    Timetable findAll();
    boolean saveSlot(TimetableSlot slot);
    boolean overwriteAllSlots(Timetable timetable);
}
