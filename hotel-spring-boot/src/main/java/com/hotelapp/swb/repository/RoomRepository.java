package com.hotelapp.swb.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelapp.swb.entity.RoomEntity;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {	
public Set<RoomEntity> findAllByRoomNo(Integer roomNo);
}
