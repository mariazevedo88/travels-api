package io.github.mariazevedo88.tripsapi.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.github.mariazevedo88.tripsapi.enumeration.TripTypeEnum;
import io.github.mariazevedo88.tripsapi.model.Statistic;
import io.github.mariazevedo88.tripsapi.model.Trip;
import io.github.mariazevedo88.tripsapi.service.StatisticService;
import io.github.mariazevedo88.tripsapi.service.TripService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TripsApiUnitTests {
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private StatisticService statisticService;
	
	@BeforeAll
	public void setUp() {
		tripService.createFactory();
		tripService.createTripList();
	}

	@Test
	@Order(1)
	public void shouldReturnNotNullTripService() {
		assertNotNull(tripService);
	}

	@Test
	@Order(2)
	public void shouldReturnNotNullStatisticService() throws Exception {
		assertNotNull(statisticService);
	}

	@Test
	@Order(3)
	@SuppressWarnings("unchecked")
	public void shouldReturnTripCreatedWithSuccess() throws Exception {
		
		LocalDateTime now = LocalDateTime.of(2020, Month.DECEMBER, 1, 20, 0, 6);
		String localDate = now.toString().concat("Z");
		
		LocalDateTime finalD = LocalDateTime.of(2020, Month.DECEMBER, 31, 20, 0, 6);
		String finalDate = finalD.toString().concat("Z");
		
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("orderCode", "220788");
		json.put("amount", "22.88");
		json.put("type", TripTypeEnum.RETURN.getValue());
		json.put("initialDate", localDate);
		json.put("finalDate", finalDate);
		
		Trip trip = tripService.create(json);
		
		assertNotNull(trip);
		assertEquals(trip.getId().intValue(), json.get("id"));
		assertEquals(trip.getOrderCode(), json.get("orderCode"));
		assertEquals(trip.getAmount().toString(), json.get("amount"));
		assertEquals(trip.getType().toString(), json.get("type"));
		assertEquals(trip.getInitialDate(), now);
		assertEquals(trip.getFinalDate(), finalD);
	}
	
	@Test
	@Order(4)
	@SuppressWarnings("unchecked")
	public void shouldReturnTripCreatedInInitialDateIsGreaterThanFinalDate() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 2);
		json.put("orderCode", "220788");
		json.put("amount", "22.88");
		json.put("type", TripTypeEnum.RETURN.getValue());
		json.put("initialDate", "2020-09-20T09:59:51.312Z");
		json.put("finalDate", "2020-09-11T09:59:51.312Z");
		
		Trip transaction = tripService.create(json);
		boolean transactionInFuture = tripService.isInitialDateGreaterThanFinalDate(transaction);
		
		assertTrue(transactionInFuture);
	}
	
	@Test
	@Order(5)
	@SuppressWarnings("unchecked")
	public void shouldReturnTripStatisticsCalculated() throws Exception {
		
		tripService.delete();
		
		LocalDateTime now = LocalDateTime.of(2020, Month.DECEMBER, 1, 20, 0, 6);
		String localDate = now.toString().concat("Z");
		
		LocalDateTime finalD = LocalDateTime.of(2020, Month.DECEMBER, 31, 20, 0, 6);
		String finalDate = finalD.toString().concat("Z");
		
		JSONObject json1 = new JSONObject();
		json1.put("id", 1);
		json1.put("orderCode", "220788");
		json1.put("amount", "22.88");
		json1.put("type", TripTypeEnum.RETURN.getValue());
		json1.put("initialDate", localDate);
		json1.put("finalDate", finalDate);
		
		Trip trip = tripService.create(json1);
		tripService.add(trip);
		
		JSONObject json2 = new JSONObject();
		json2.put("id", 2);
		json2.put("orderCode", "300691");
		json2.put("amount", "120.0");
		json2.put("type", TripTypeEnum.ONE_WAY.getValue());
		json2.put("initialDate", localDate);
		json2.put("finalDate", finalDate);
		
		trip = tripService.create(json2);
		tripService.add(trip);
		
		Statistic statistic = statisticService.create(tripService.find());
		
		assertNotNull(statistic);
		assertEquals("142.88", statistic.getSum().toString());
		assertEquals("71.44", statistic.getAvg().toString());
		assertEquals("22.88", statistic.getMin().toString());
		assertEquals("120.00", statistic.getMax().toString());
		assertEquals(2, statistic.getCount());
	}
	
	@AfterAll
	public void tearDown() {
		tripService.clearObjects();
	}

}
