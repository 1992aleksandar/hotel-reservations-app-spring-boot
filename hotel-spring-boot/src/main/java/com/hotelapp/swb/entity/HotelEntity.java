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
}
