package com.hotelapp.swb.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingEntity {

	@Id
	@GeneratedValue
	private Integer bookingNo;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RoomEntity room;

	@Column(nullable = false)
	private Integer start;

	@Column(nullable = false)
	private Integer end;

	public BookingEntity() {
	}

	public BookingEntity(RoomEntity room, Integer start, Integer end) {
		this.room = room;
		this.start = start;
		this.end = end;
	}

	public Integer getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(Integer bookingNo) {
		this.bookingNo = bookingNo;
	}

	public RoomEntity getRoom() {
		return room;
	}

	public void setRoom(RoomEntity room) {
		this.room = room;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
}
