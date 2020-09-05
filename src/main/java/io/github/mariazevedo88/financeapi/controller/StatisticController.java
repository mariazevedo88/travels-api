package io.github.mariazevedo88.financeapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.financeapi.model.Statistic;
import io.github.mariazevedo88.financeapi.model.Transaction;
import io.github.mariazevedo88.financeapi.service.StatisticService;
import io.github.mariazevedo88.financeapi.service.TransactionService;

/**
 * SpringBoot RestController that creates all service endpoints related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@RestController
@RequestMapping("/financial/v1/statistics")
public class StatisticController {
	
	private static final Logger logger = Logger.getLogger(StatisticController.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private StatisticService statisticsService;
	
	
	/**
	 * Method that returns the statistics based on the transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @return ResponseEntity - 200
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Statistic> getStatistics() {
		
		List<Transaction> transactions = transactionService.find();
		Statistic statistics = statisticsService.create(transactions);
		
		logger.info(statistics);
		
		return ResponseEntity.ok(statistics);
	}

}
