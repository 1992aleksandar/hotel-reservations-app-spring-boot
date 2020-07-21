package com.hotelapp.swb.service.unit;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.hotelapp.swb.ApplicationStartup;
import com.hotelapp.swb.HotelAppSwbApplication;
import com.hotelapp.swb.service.HotelService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HotelAppSwbApplication.class, properties = { "spring.config.name=myapp-test-h2",
		"myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus" })	
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class HotelServiceTests {

	@Autowired
	HotelService hotelService;
	
	@MockBean
	private ApplicationStartup applicationStartup;

	@Test
	@Transactional
	public void testBooking1() {
		hotelService.createHotel(1);

		Boolean booking1 = hotelService.booking(-4, 2);
		Boolean booking2 = hotelService.booking(200, 400);
		assertFalse(booking1);
		assertFalse(booking2);
	}

	@Test
	@Transactional
	public void testBooking2() {
		hotelService.createHotel(3);

		Boolean booking1 = hotelService.booking(0, 5);
		Boolean booking2 = hotelService.booking(7, 13);
		Boolean booking3 = hotelService.booking(3, 9);
		Boolean booking4 = hotelService.booking(5, 7);
		Boolean booking5 = hotelService.booking(6, 6);
		Boolean booking6 = hotelService.booking(0, 4);

		assertTrue(booking1);
		assertTrue(booking2);
		assertTrue(booking3);
		assertTrue(booking4);
		assertTrue(booking5);
		assertTrue(booking6);
	}

	@Test
	@Transactional
	public void testBooking3() {
		hotelService.createHotel(3);

		Boolean booking1 = hotelService.booking(1, 3);
		Boolean booking2 = hotelService.booking(2, 5);
		Boolean booking3 = hotelService.booking(1, 9);
		Boolean booking4 = hotelService.booking(0, 15);

		assertTrue(booking1);
		assertTrue(booking2);
		assertTrue(booking3);
		assertFalse(booking4);
	}

	@Test
	@Transactional
	public void testBooking4() {
		hotelService.createHotel(3);

		Boolean booking1 = hotelService.booking(1, 3);
		Boolean booking2 = hotelService.booking(0, 15);
		Boolean booking3 = hotelService.booking(1, 9);
		Boolean booking4 = hotelService.booking(2, 5);
		Boolean booking5 = hotelService.booking(4, 9);

		assertTrue(booking1);
		assertTrue(booking2);
		assertTrue(booking3);
		assertFalse(booking4);
		assertTrue(booking5);
	}

	@Test
	@Transactional
	public void testBooking5() {
		hotelService.createHotel(2);

		Boolean booking1 = hotelService.booking(1, 3);
		Boolean booking2 = hotelService.booking(0, 4);
		Boolean booking3 = hotelService.booking(2, 3);
		Boolean booking4 = hotelService.booking(5, 5);
		Boolean booking5 = hotelService.booking(4, 10);
		Boolean booking6 = hotelService.booking(10, 10);
		Boolean booking7 = hotelService.booking(6, 7);
		Boolean booking8 = hotelService.booking(8, 10);
		Boolean booking9 = hotelService.booking(8, 9);

		assertTrue(booking1);
		assertTrue(booking2);
		assertFalse(booking3);
		assertTrue(booking4);
		assertTrue(booking5);
		assertTrue(booking6);
		assertTrue(booking7);
		assertFalse(booking8);
		assertTrue(booking9);
	}
}