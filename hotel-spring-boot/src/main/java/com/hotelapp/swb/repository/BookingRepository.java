package com.hotelapp.swb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelapp.swb.entity.BookingEntity;
import com.hotelapp.swb.entity.RoomEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {			
		 public BookingEntity getBookingByBookingNo(Integer roomId);
		 public List<BookingEntity> findAllByRoom(RoomEntity room);
}
