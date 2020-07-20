package com.hotelapp.swb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelapp.swb.entity.RoomEntity;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {	
public RoomEntity getRoomByRoomNo(Integer roomNo);
}
