package com.hotelapp.swb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelapp.swb.entity.HotelEntity;

@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Integer> {	
}
