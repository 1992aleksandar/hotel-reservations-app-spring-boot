package com.hotelapp.swb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelapp.swb.entity.BookingEntity;
import com.hotelapp.swb.entity.HotelEntity;
import com.hotelapp.swb.entity.RoomEntity;
import com.hotelapp.swb.repository.BookingRepository;
import com.hotelapp.swb.repository.RoomRepository;

@Service
public class HotelService {	
	
	private HotelEntity hotel;

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private BookingRepository bookingRepository;		
	
	public void createHotel(Integer size) {
		if ((size < 0) || (size > 1000))
			throw new RuntimeException("Invalid room number");
		
		hotel=new HotelEntity(size);

		for (int i = 0; i < size; i++) {
			RoomEntity room = new RoomEntity(i, hotel);
			roomRepository.save(room);
		}		
	}

	public Boolean booking(Integer start, Integer end) {
		Integer size = hotel.getHotelSize();		
		ArrayList<RoomEntity> availableRooms = new ArrayList<>();
		RoomEntity room = null;

		if ((start < 0) ||(end >= 365))
			return false;

		if (start > end)
			return false;

		for (int i = 0; i < size; i++) {
			room =getRoomByRoomNo(hotel,i);

			if (roomIsAvailable(room, start, end)) {
				availableRooms.add(room);
			}
		}

		if (!availableRooms.isEmpty()) {
			RoomEntity selectedRoom=roomSelection(availableRooms, start, end);
			BookingEntity newBooking = new BookingEntity(selectedRoom,start, end);
			selectedRoom.getBookings().add(newBooking);
			return true;
		}
		return false;
	}
	
	public RoomEntity roomSelection(List<RoomEntity> availableRooms, Integer start, Integer end) {
		RoomEntity selectedRoom = availableRooms.get(0);
		Integer minDistance = selectedRoom.distanceFromOtherBookings(start, end);

		for (int i = 1; i < hotel.getHotelSize(); i++) {
			if (getRoomByRoomNo(hotel, i).distanceFromOtherBookings(start, end) < minDistance) {
				minDistance = getRoomByRoomNo(hotel, i).distanceFromOtherBookings(start, end);
				selectedRoom = getRoomByRoomNo(hotel,i);
			}
		}
		
		return selectedRoom;
	}	

	public Boolean roomIsAvailable(RoomEntity room, Integer start, Integer end) {
		List<BookingEntity> bookings = bookingRepository.findAllByRoom(room);
		Boolean available = true;

		for (BookingEntity booking : bookings) {
			for (int j = start; j <= end; j++) {
				if (j >= booking.getStart() && j <= booking.getEnd()) {
					available = false;
					break;
				}
			}
		}
		
		return available;
	}

	public void roomReservation() {
		Integer size = hotel.getHotelSize();
		for (int i = 0; i < size; i++) {
			if (roomRepository.getRoomByRoomNo(i) == null) {
				System.out.println("no rooms reserved");
				break;
			}
			RoomEntity room = roomRepository.getRoomByRoomNo(i);
			System.out.println("roomNo " + room.getRoomNo());
			List<BookingEntity> bookings = bookingRepository.findAllByRoom(room);
			for (BookingEntity booking : bookings) {
				System.out.println("booking start date: " + booking.getStart() + " and end date: " + booking.getEnd());
			}
		}
	}	
	
	public RoomEntity getRoomByRoomNo(HotelEntity hotel, Integer roomNo) {
	     if ((roomNo<0) || (roomNo>(hotel.getHotelSize()-1)))
	    	 throw new RuntimeException("No room with such id");
			
			return roomRepository.getRoomByRoomNo(roomNo);
		}	

	public void customerCommunication() {
		try (Scanner scanner = new Scanner(System.in)) {			
			Integer start;
			Integer end;
			Integer roomNumber;

			do {
				System.out.println("Please enter number of rooms the hotel has. Maximum number of rooms is 1000.");
				roomNumber = scanner.nextInt();
			} while (roomNumber > 1000 || roomNumber < 1);

			createHotel(roomNumber);
			String input;

			do {				
				System.out.println("Please enter the booking start day.");
				start = scanner.nextInt();
				System.out.println("Please enter the booking end day.");
				end = scanner.nextInt();

				if (booking(start, end))
					System.out.println("Booking (" + start + ", " + end + ")" + " accepted");
				else
					System.out.println("Booking (" + start + ", " + end + ")" + " declined");

				System.out.println("Another booking? Press y for yes or any other key to exit the program.");
				input = scanner.next();

			} while (input.equals("y") || input.equals("Y"));
		}
		System.out.println("Exiting the program.");
	}
}
