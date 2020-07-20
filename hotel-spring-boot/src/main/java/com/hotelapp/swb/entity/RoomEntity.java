package com.hotelapp.swb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity {

	@Id
	private Integer roomNo;
	
	@OneToMany(mappedBy="room",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<BookingEntity> bookings;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private  HotelEntity hotel;
	
	public RoomEntity() {
	}

	public RoomEntity(Integer roomNo, HotelEntity hotel) {	
		this.roomNo = roomNo;
		this.hotel = hotel;
		hotel.getHotelRooms().add(this);
		this.bookings=new HashSet<>();
	}

	public Integer getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}

	public Set<BookingEntity> getBookings() {
		return bookings;
	}

	public void setBookings(Set<BookingEntity> bookings) {
		this.bookings = bookings;
	}
	
	public HotelEntity getHotel() {
		return hotel;
	}

	public void setHotel(HotelEntity hotel) {
		this.hotel = hotel;
	}

	public Integer distanceFromOtherBookings(Integer start, Integer end) {
		Integer closestLeft=0;
		Integer closestRight=0;	
		
		for (BookingEntity booking:bookings) {
			if (booking.getEnd()<start && booking.getEnd()>closestLeft)
				closestLeft=booking.getEnd();	
			
			if (booking.getStart()>end && booking.getStart()<closestRight)
				closestRight=booking.getStart();		
		}		
		
		Integer distance=(start-closestLeft)+(closestRight-end);
		
		return distance;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomNo == null) ? 0 : roomNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomEntity other = (RoomEntity) obj;
		if (roomNo == null) {
			if (other.roomNo != null)
				return false;
		} else if (!roomNo.equals(other.roomNo))
			return false;
		return true;
	}
}
