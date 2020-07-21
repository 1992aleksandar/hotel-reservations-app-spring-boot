package com.hotelapp.swb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelapp.swb.entity.BookingEntity;
import com.hotelapp.swb.entity.HotelEntity;
import com.hotelapp.swb.entity.RoomEntity;
import com.hotelapp.swb.repository.BookingRepository;
import com.hotelapp.swb.repository.HotelRepository;
import com.hotelapp.swb.repository.RoomRepository;

@Service
public class HotelService {

	private HotelEntity hotel;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private HotelRepository hotelRepository;

	public void createHotel(Integer size) {
		if ((size < 1) || (size > 1000))
			throw new RuntimeException("Invalid room number");

		hotel = new HotelEntity(size);

		for (int i = 1; i <= size; i++) {
			RoomEntity room = new RoomEntity(i);
			hotel.addRoom(room);
		}

		hotelRepository.save(hotel);
	}

	public Boolean booking(Integer start, Integer end) {
		Integer size = hotel.getHotelSize();
		ArrayList<RoomEntity> availableRooms = new ArrayList<>();
		RoomEntity room = null;

		if ((start < 0) || (end >= 365))
			return false;

		if (start > end)
			return false;

		for (int i = 1; i <= size; i++) {
			room = getRoomByRoomNo(hotel, i);

			if (roomIsAvailable(room, start, end)) {
				availableRooms.add(room);
			}
		}

		if (!availableRooms.isEmpty()) {
			RoomEntity selectedRoom = roomSelection(availableRooms, start, end);
			BookingEntity newBooking = new BookingEntity(start, end);
			selectedRoom.addBooking(newBooking);
			
			roomRepository.save(selectedRoom);
			
			return true;
		}
		
		return false;
	}

	public RoomEntity roomSelection(List<RoomEntity> availableRooms, Integer start, Integer end) {
		RoomEntity selectedRoom = availableRooms.get(0);
		Integer minDistance = selectedRoom.distanceFromOtherBookings(start, end);

		for (int i = 1; i < availableRooms.size(); i++) {
			RoomEntity room = availableRooms.get(i);
			Integer distance = room.distanceFromOtherBookings(start, end);

			if (distance < minDistance) {
				minDistance = distance;
				selectedRoom = room;
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
	
	public RoomEntity getRoomByRoomNo(HotelEntity hotel, Integer roomNo) {
		if ((roomNo < 1) || (roomNo > hotel.getHotelSize()))
			throw new RuntimeException("No room with such id");

		RoomEntity room = null;
		Set<RoomEntity> hotelRooms = hotel.getHotelRooms();
		Iterator<RoomEntity> iterator = hotelRooms.iterator();

		while (iterator.hasNext()) {
			RoomEntity currentRoom = iterator.next();
			if (currentRoom.getRoomNo() == roomNo) {
				room = currentRoom;
				break;
			}
		}

		return room;
	}

	public void customerCommunication() throws InterruptedException {
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
		Thread.sleep(2000);	
	}
}
