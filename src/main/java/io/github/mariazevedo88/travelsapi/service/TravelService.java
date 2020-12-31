package io.github.mariazevedo88.travelsapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.travelsapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsapi.factory.TravelFactory;
import io.github.mariazevedo88.travelsapi.factory.impl.TravelFactoryImpl;
import io.github.mariazevedo88.travelsapi.model.Travel;

/**
 * Service that implements methods related to travels.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@Service
public class TravelService {
	
	private TravelFactory factory;
	
	private List<Travel> travels;
	
	/**
	 * Method to create TripFactory
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void createFactory() {
		if(factory == null) {
			factory = new TravelFactoryImpl();
		}
	}
	
	/**
	 * Method to create the travel's list
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void createTravelList() {
		if(travels == null) {
			travels = new ArrayList<>();
		}
	}
	
	/**
	 * Method that check if JSON is valid.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param jsonInString
	 * @return boolean
	 */
	public boolean isJSONValid(String jsonInString) {
	    try {
	       return new ObjectMapper().readTree(jsonInString) != null;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	/**
	 * Method to parse the id field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @return long
	 */
	private long parseId(JSONObject travel) {
		return Long.valueOf((int) travel.get("id"));
	}
	
	/**
	 * Method to parse the amount field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @return BigDecimal
	 */
	private BigDecimal parseAmount(JSONObject travel) {
		return new BigDecimal((String) travel.get("amount"));
	}
	
	/**
	 * Method to parse the startDate field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @return LocalDateTime
	 */
	private LocalDateTime parseInitialDate(JSONObject travel) {
		var startDate = (String) travel.get("startDate");
		return ZonedDateTime.parse(startDate).toLocalDateTime();
	}
	
	/**
	 * Method to parse the endDate field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @return LocalDateTime
	 */
	private LocalDateTime parseFinalDate(JSONObject travel) {
		var endDate = (String) travel.get("endDate");
		return ZonedDateTime.parse(endDate).toLocalDateTime();
	}
	
	/**
	 * Method that check if the travel is being finished in the future.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @return boolean
	 */
	public boolean isStartDateGreaterThanEndDate(Travel travel) {
		if (travel.getEndDate() == null) return false;
		return travel.getStartDate().isAfter(travel.getEndDate());
	}
	
	/**
	 * Method to fullfil the Trip object
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param jsonTravel
	 * @param travel
	 */
	private void setTripValues(JSONObject jsonTravel, Travel travel) {
		
		String orderNumber = (String) jsonTravel.get("orderNumber");
		String type = (String) jsonTravel.get("type");
		
		travel.setOrderNumber(orderNumber != null ? orderNumber : travel.getOrderNumber());
		travel.setAmount(jsonTravel.get("amount") != null ? parseAmount(jsonTravel) : travel.getAmount());
		travel.setStartDate(jsonTravel.get("initialDate") != null ? parseInitialDate(jsonTravel) : travel.getStartDate());
		travel.setEndDate(jsonTravel.get("finalDate") != null ? parseFinalDate(jsonTravel) : travel.getEndDate());
		travel.setType(type != null ? TravelTypeEnum.getEnum(type) : travel.getType());
	}
	
	/**
	 * Method to create a trip
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param jsonTravel
	 * @return Travel
	 */
	public Travel create(JSONObject jsonTravel) {
		
		createFactory();
		
		Travel travel = factory.createTravel((String) jsonTravel.get("type"));
		travel.setId(parseId(jsonTravel));
		setTripValues(jsonTravel, travel);
		
		return travel;
	}
	
	/**
	 * Method to update a trip
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 * @param jsonTravel
	 * 
	 * @return Travel
	 */
	public Travel update(Travel travel, JSONObject jsonTravel) {
		
		setTripValues(jsonTravel, travel);
		return travel;
	}

	/**
	 * Method that add an object Travel
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travel
	 */
	public void add(Travel travel) {
		createTravelList();
		travels.add(travel);
	}

	/**
	 * Method that get all trips
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param travels
	 * @return List
	 */
	public List<Travel> find() {
		createTravelList();
		return travels;
	}
	
	/**
	 * Method that get travels by id
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param id
	 * @return Trip
	 */
	public Travel findById(long id) {
		return travels.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Method that deletes the travel created
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void delete() {
		travels.clear();
	}
	
	/**
	 * Method to clean objects
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void clearObjects() {
		travels = null;
		factory = null;
	}

}
