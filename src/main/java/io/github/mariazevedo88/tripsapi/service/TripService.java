package io.github.mariazevedo88.tripsapi.service;

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

import io.github.mariazevedo88.tripsapi.enumeration.TripTypeEnum;
import io.github.mariazevedo88.tripsapi.factory.TripFactory;
import io.github.mariazevedo88.tripsapi.factory.impl.TripFactoryImpl;
import io.github.mariazevedo88.tripsapi.model.Trip;

/**
 * Service that implements methods related to a trip.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@Service
public class TripService {
	
	private TripFactory factory;
	
	private List<Trip> trips;
	
	/**
	 * Method to create TripFactory
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void createFactory() {
		if(factory == null) {
			factory = new TripFactoryImpl();
		}
	}
	
	/**
	 * Method to create the trip list
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void createTripList() {
		if(trips == null) {
			trips = new ArrayList<>();
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
	 * @param trip
	 * @return long
	 */
	private long parseId(JSONObject trip) {
		return Long.valueOf((int) trip.get("id"));
	}
	
	/**
	 * Method to parse the amount field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 * @return BigDecimal
	 */
	private BigDecimal parseAmount(JSONObject trip) {
		return new BigDecimal((String) trip.get("amount"));
	}
	
	/**
	 * Method to parse the initialDate field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 * @return LocalDateTime
	 */
	private LocalDateTime parseInitialDate(JSONObject trip) {
		var tripDate = (String) trip.get("initialDate");
		return ZonedDateTime.parse(tripDate).toLocalDateTime();
	}
	
	/**
	 * Method to parse the finalDate field.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 * @return LocalDateTime
	 */
	private LocalDateTime parseFinalDate(JSONObject trip) {
		var tripDate = (String) trip.get("finalDate");
		return ZonedDateTime.parse(tripDate).toLocalDateTime();
	}
	
	/**
	 * Method that check if the trip is being finished in the future.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 * @return boolean
	 */
	public boolean isInitialDateGreaterThanFinalDate(Trip trip) {
		if (trip.getFinalDate() == null) return false;
		return trip.getInitialDate().isAfter(trip.getFinalDate());
	}
	
	/**
	 * Method to fullfil the Trip object
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param jsonTrip
	 * @param trip
	 */
	private void setTripValues(JSONObject jsonTrip, Trip trip) {
		
		String orderCode = (String) jsonTrip.get("orderCode");
		String type = (String) jsonTrip.get("type");
		
		trip.setOrderCode(orderCode != null ? orderCode : trip.getOrderCode());
		trip.setAmount(jsonTrip.get("amount") != null ? parseAmount(jsonTrip) : trip.getAmount());
		trip.setInitialDate(jsonTrip.get("initialDate") != null ? parseInitialDate(jsonTrip) : trip.getInitialDate());
		trip.setFinalDate(jsonTrip.get("finalDate") != null ? parseFinalDate(jsonTrip) : trip.getFinalDate());
		trip.setType(type != null ? TripTypeEnum.getEnum(type) : trip.getType());
	}
	
	/**
	 * Method to create a trip
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param jsonTrip
	 * @return Trip
	 */
	public Trip create(JSONObject jsonTrip) {
		
		createFactory();
		
		Trip trip = factory.createTrip((String) jsonTrip.get("type"));
		trip.setId(parseId(jsonTrip));
		setTripValues(jsonTrip, trip);
		
		return trip;
	}
	
	/**
	 * Method to update a trip
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 * @param jsonTrip
	 * 
	 * @return Trip
	 */
	public Trip update(Trip trip, JSONObject jsonTrip) {
		
		setTripValues(jsonTrip, trip);
		return trip;
	}

	/**
	 * Method that add a trip
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip
	 */
	public void add(Trip trip) {
		createTripList();
		trips.add(trip);
	}

	/**
	 * Method that get all trips
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trips
	 * @return List
	 */
	public List<Trip> find() {
		createTripList();
		return trips;
	}
	
	/**
	 * Method that get a trip by id
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param id
	 * @return Trip
	 */
	public Trip findById(long id) {
		return trips.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Method that deletes the trips created
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void delete() {
		trips.clear();
	}
	
	/**
	 * Method to clean objects
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 */
	public void clearObjects() {
		trips = null;
		factory = null;
	}

}
