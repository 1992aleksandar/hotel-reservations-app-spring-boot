package com.hotelapp.swb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hotelapp.swb.entity.compositeprimarykeys.BookingId;

@Entity
@Table(name = "bookings")
@IdClass(BookingId.class)
public class BookingEntity {

    @Id
	@Column(nullable = false)
	private Integer start;

    @Id
	@Column(nullable = false)
	private Integer end;		
	
    @Id
	@ManyToOne(fetch = FetchType.LAZY)
	private RoomEntity room;
	

	public BookingEntity() {
	}

	public BookingEntity(Integer start, Integer end) {		
		this.start = start;
		this.end = end;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end);
		result = prime * result + ((room == null) ? 0 : room.getRoomNo());
		result = prime * result + ((start == null) ? 0 : start);
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
		BookingEntity other = (BookingEntity) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.getRoomNo().equals(other.room.getRoomNo()))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}	
}
