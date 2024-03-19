package com.kingslandinghotelandsuites.kingslandinghotelandsuites.controller;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions.InvalidBookingRequestException;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions.ResourceNotFoundException;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.BookedRoom;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.Room;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.response.BookingResponse;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.response.RoomResponse;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.BookingService;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("confirmationCode/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(), room);
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> SAVEBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest) {
        try {
            String confirmationCode =
                    bookingService.saveBooking(roomId, bookingRequest);
                return ResponseEntity.ok(
                        "Room booked successfully, Your booking confirmation code is " + confirmationCode);
        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

}
