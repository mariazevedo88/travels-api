package io.github.mariazevedo88.tripsapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.tripsapi.model.Statistic;
import io.github.mariazevedo88.tripsapi.model.Trip;
import io.github.mariazevedo88.tripsapi.service.StatisticService;
import io.github.mariazevedo88.tripsapi.service.TripService;

/**
 * SpringBoot RestController that creates all service end-points related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@RestController
@RequestMapping("/tripsapi/v1/statistics")
public class StatisticController {
	
	private static final Logger logger = Logger.getLogger(StatisticController.class);
	
	@Autowired
	private TripService tripsService;
	
	@Autowired
	private StatisticService statisticsService;
	
	
	/**
	 * Method that returns the statistics based on the trips
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @return ResponseEntity - 200
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Statistic> getStatistics() {
		
		List<Trip> trips = tripsService.find();
		Statistic statistics = statisticsService.create(trips);
		
		logger.info(statistics);
		
		return ResponseEntity.ok(statistics);
	}

}
