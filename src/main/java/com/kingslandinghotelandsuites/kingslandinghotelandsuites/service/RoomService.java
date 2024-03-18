package com.kingslandinghotelandsuites.kingslandinghotelandsuites.service;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {
    Room addNewRoom(MultipartFile file,
                    String roomType,
                    BigDecimal roomPrice) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;
}
