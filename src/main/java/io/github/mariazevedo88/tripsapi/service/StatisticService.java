package io.github.mariazevedo88.tripsapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.mariazevedo88.tripsapi.model.Statistic;
import io.github.mariazevedo88.tripsapi.model.Trip;

/**
 * Service that implements methods related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@Service
public class StatisticService {
	
	/**
	 * Method that creates statistics based on the transactions.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/09/2019
	 * 
	 * @param trips
	 * @return Statistic
	 */
	public Statistic create(List<Trip> trips) {
		
		var statistics = new Statistic();
		statistics.setCount(trips.stream().count());
		statistics.setAvg(BigDecimal.valueOf(trips.stream().mapToDouble(t -> t.getAmount().doubleValue()).average().orElse(0.0))
				.setScale(2, RoundingMode.HALF_UP));
		statistics.setMin(BigDecimal.valueOf(trips.stream().mapToDouble(t -> t.getAmount().doubleValue()).min().orElse(0.0))
				.setScale(2, RoundingMode.HALF_UP));
		statistics.setMax(BigDecimal.valueOf(trips.stream().mapToDouble(t -> t.getAmount().doubleValue()).max().orElse(0.0))
				.setScale(2, RoundingMode.HALF_UP));
		statistics.setSum(BigDecimal.valueOf(trips.stream().mapToDouble(t -> t.getAmount().doubleValue()).sum())
				.setScale(2, RoundingMode.HALF_UP));
		
		return statistics;
	}

}
