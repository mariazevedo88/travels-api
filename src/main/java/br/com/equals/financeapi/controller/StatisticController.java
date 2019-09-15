package br.com.equals.financeapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.equals.financeapi.model.Statistic;
import br.com.equals.financeapi.model.Transaction;
import br.com.equals.financeapi.service.StatisticService;
import br.com.equals.financeapi.service.TransactionService;

/**
 * SpringBoot RestController that creates all service endpoints related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@RestController
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
	@GetMapping(path = "/statistics", produces = { "application/json" })
	public ResponseEntity<Statistic> getStatistics() {
		
		List<Transaction> transactions = transactionService.find();
		Statistic statistics = statisticsService.create(transactions);
		
		logger.info(statistics);
		
		return ResponseEntity.ok(statistics);
	}

}
