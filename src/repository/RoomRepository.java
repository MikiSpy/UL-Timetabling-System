package repository;

import model.Room;

public interface RoomRepository {
    boolean save(Room room);
    boolean delete(String room);
    Room findByNumber(String number);
}
