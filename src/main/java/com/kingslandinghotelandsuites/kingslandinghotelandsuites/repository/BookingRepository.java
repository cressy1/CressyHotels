package com.kingslandinghotelandsuites.kingslandinghotelandsuites.repository;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    List<BookedRoom> findByRoomId(Long roomId);

    BookedRoom findByConfirmationCode(String confirmationCode);
}
