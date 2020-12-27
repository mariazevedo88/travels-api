package io.github.mariazevedo88.tripsapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.mariazevedo88.tripsapi.model.Trip;
import io.github.mariazevedo88.tripsapi.service.TripService;

/**
 * SpringBoot RestController that creates all service end-points related to the trips.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@RestController
@RequestMapping("/tripsapi/v1/trips")
public class TripController {
	
	private static final Logger logger = Logger.getLogger(TripController.class);
	
	@Autowired
	private TripService tripService;
	
	/**
	 * Method that list all trips
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @return ResponseEntity with a <code>List<Trip></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Trip>> find() {
		if(tripService.find().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		logger.info(tripService.find());
		return ResponseEntity.ok(tripService.find());
	}
	
	/**
	 * Method that deletes all existing trips.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @return Returns an empty body with one of the following:
	 * 
	 * 204 - if delete with success
	 * 205 - if hasn't delete with success.
	 * 500 - Server Errors: something went wrong on API end (These are rare).
	 */
	@DeleteMapping
	public ResponseEntity<Boolean> delete() {
		try {
			tripService.delete();
			return ResponseEntity.noContent().build();
		}catch(Exception e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * Method that creates a trip.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip, where: 
	 * 
	 * id - trip id; 
	 * orderCode - identification number of a trip in the system; 
	 * amount – transaction amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * initialDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone;
	 * finalDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; 
	 * type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * 
	 * @return ResponseEntity with a <code>Trip</code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 422 - Unprocessable Entity: if any of the fields are not parsable or the initial date is greater than final date.
	 * 500 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<Trip> create(@RequestBody JSONObject trip) {
		try {
			if(tripService.isJSONValid(trip.toString())) {
				Trip tripCreated = tripService.create(trip);
				var uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path(tripCreated.getOrderCode()).build().toUri();
				
				if(tripService.isInitialDateGreaterThanFinalDate(tripCreated)){
					logger.error("The initial date is greater than final date.");
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
				}else {
					tripService.add(tripCreated);
					return ResponseEntity.created(uri).body(null);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable. " + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	/**
	 * Method that updates a trip.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trip, where: 
	 * 
	 * id - trip id; 
	 * orderCode - identification number of a trip in the system; 
	 * amount – transaction amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * initialDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone;
	 * finalDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; 
	 * type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * 
	 * @return Returns an empty body with one of the following:
	 * 200 – OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 422 - Unprocessable Entity: if any of the fields are not parsable or the initial date is greater than final date.
	 * 500 - Server Errors: something went wrong on API end (These are rare).
	 */
	@PutMapping(path = "/{id}", produces = { "application/json" })
	public ResponseEntity<Trip> update(@PathVariable("id") long id, @RequestBody JSONObject trip) {
		try {
			if(tripService.isJSONValid(trip.toString())) {
				Trip tripToUpdate = tripService.findById(id);
				if(tripToUpdate == null){
					logger.error("The trip not found.");
					return ResponseEntity.notFound().build(); 
				}else {
					Trip tripUpdated = tripService.update(tripToUpdate, trip);
					return ResponseEntity.ok(tripUpdated);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable." + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

}
