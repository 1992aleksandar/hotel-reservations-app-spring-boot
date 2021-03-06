package com.hotelapp.swb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="hotels")
public class HotelEntity {
	
	@Id
	@GeneratedValue
	private Integer hotelId;
	
	private Integer hotelSize;
	
	@OneToMany(mappedBy="hotel",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<RoomEntity> hotelRooms;	
	
	public HotelEntity() {		
	}
	
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public HotelEntity(Integer hotelSize) {
		this.hotelSize = hotelSize;
		this.hotelRooms=new HashSet<>();
	}
	
	public Integer getHotelSize() {
		return hotelSize;
	}
	
	public void setHotelSize(Integer hotelSize) {
		this.hotelSize = hotelSize;
	}
	
	public Set<RoomEntity> getHotelRooms() {
		return hotelRooms;
	}
	
	public void setHotelRooms(HashSet<RoomEntity> hotelRooms) {
		this.hotelRooms = hotelRooms;
	}
	
	public void addRoom(RoomEntity room) {
		this.getHotelRooms().add(room);
		room.setHotel(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotelId == null) ? 0 : hotelId);
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
		HotelEntity other = (HotelEntity) obj;
		if (hotelId == null) {
			if (other.hotelId != null)
				return false;
		} else if (!hotelId.equals(other.hotelId))
			return false;
		return true;
	}		
}
