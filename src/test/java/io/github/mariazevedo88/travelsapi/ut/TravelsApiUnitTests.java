package io.github.mariazevedo88.travelsapi.ut;

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

import io.github.mariazevedo88.travelsapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsapi.model.Statistic;
import io.github.mariazevedo88.travelsapi.model.Travel;
import io.github.mariazevedo88.travelsapi.service.StatisticService;
import io.github.mariazevedo88.travelsapi.service.TravelService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TravelsApiUnitTests {
	
	@Autowired
	private TravelService travelsService;
	
	@Autowired
	private StatisticService statisticService;
	
	@BeforeAll
	public void setUp() {
		travelsService.createFactory();
		travelsService.createTravelList();
	}

	@Test
	@Order(1)
	public void shouldReturnNotNullTravelService() {
		assertNotNull(travelsService);
	}

	@Test
	@Order(2)
	public void shouldReturnNotNullStatisticService() throws Exception {
		assertNotNull(statisticService);
	}

	@Test
	@Order(3)
	@SuppressWarnings("unchecked")
	public void shouldReturnTravelCreatedWithSuccess() throws Exception {
		
		LocalDateTime initialDate = LocalDateTime.of(2020, Month.DECEMBER, 1, 20, 0, 6);
		String startDate = initialDate.toString().concat("Z");
		
		LocalDateTime finalDate = LocalDateTime.of(2020, Month.DECEMBER, 31, 20, 0, 6);
		String endDate = finalDate.toString().concat("Z");
		
		JSONObject jsonTravel = new JSONObject();
		jsonTravel.put("id", 1);
		jsonTravel.put("orderNumber", "220788");
		jsonTravel.put("amount", "22.88");
		jsonTravel.put("type", TravelTypeEnum.RETURN.getValue());
		jsonTravel.put("startDate", startDate);
		jsonTravel.put("endDate", endDate);
		
		Travel travel = travelsService.create(jsonTravel);
		
		assertNotNull(travel);
		assertEquals(travel.getId().intValue(), jsonTravel.get("id"));
		assertEquals(travel.getOrderNumber(), jsonTravel.get("orderNumber"));
		assertEquals(travel.getAmount().toString(), jsonTravel.get("amount"));
		assertEquals(travel.getType().toString(), jsonTravel.get("type"));
		assertEquals(travel.getStartDate(), initialDate);
		assertEquals(travel.getEndDate(), finalDate);
	}
	
	@Test
	@Order(4)
	@SuppressWarnings("unchecked")
	public void shouldReturnTravelCreatedInStartDateIsGreaterThanEndDate() throws Exception {
		
		JSONObject jsonTravel = new JSONObject();
		jsonTravel.put("id", 2);
		jsonTravel.put("orderNumber", "220788");
		jsonTravel.put("amount", "22.88");
		jsonTravel.put("type", TravelTypeEnum.RETURN.getValue());
		jsonTravel.put("startDate", "2020-09-20T09:59:51.312Z");
		jsonTravel.put("endDate", "2020-09-11T09:59:51.312Z");
		
		Travel travel = travelsService.create(jsonTravel);
		boolean travelInFuture = travelsService.isStartDateGreaterThanEndDate(travel);
		
		assertTrue(travelInFuture);
	}
	
	@Test
	@Order(5)
	@SuppressWarnings("unchecked")
	public void shouldReturnTravelsStatisticsCalculated() throws Exception {
		
		travelsService.delete();
		
		LocalDateTime initialDate = LocalDateTime.of(2020, Month.DECEMBER, 1, 20, 0, 6);
		String startDate = initialDate.toString().concat("Z");
		
		LocalDateTime finalDate = LocalDateTime.of(2020, Month.DECEMBER, 31, 20, 0, 6);
		String endDate = finalDate.toString().concat("Z");
		
		JSONObject jsonTravel220788 = new JSONObject();
		jsonTravel220788.put("id", 1);
		jsonTravel220788.put("orderCode", "220788");
		jsonTravel220788.put("amount", "22.88");
		jsonTravel220788.put("type", TravelTypeEnum.RETURN.getValue());
		jsonTravel220788.put("initialDate", startDate);
		jsonTravel220788.put("finalDate", endDate);
		
		Travel travel = travelsService.create(jsonTravel220788);
		travelsService.add(travel);
		
		JSONObject jsonTravel300691 = new JSONObject();
		jsonTravel300691.put("id", 2);
		jsonTravel300691.put("orderCode", "300691");
		jsonTravel300691.put("amount", "120.0");
		jsonTravel300691.put("type", TravelTypeEnum.ONE_WAY.getValue());
		jsonTravel300691.put("initialDate", startDate);
		jsonTravel300691.put("finalDate", endDate);
		
		travel = travelsService.create(jsonTravel300691);
		travelsService.add(travel);
		
		Statistic statistic = statisticService.create(travelsService.find());
		
		assertNotNull(statistic);
		assertEquals("142.88", statistic.getSum().toString());
		assertEquals("71.44", statistic.getAvg().toString());
		assertEquals("22.88", statistic.getMin().toString());
		assertEquals("120.00", statistic.getMax().toString());
		assertEquals(2, statistic.getCount());
	}
	
	@AfterAll
	public void tearDown() {
		travelsService.clearObjects();
	}

}
