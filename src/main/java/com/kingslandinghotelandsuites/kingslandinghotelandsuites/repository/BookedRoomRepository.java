package com.kingslandinghotelandsuites.kingslandinghotelandsuites.repository;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
}
