package io.github.mariazevedo88.travelsapi.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements the Statistic structure.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
	
	private BigDecimal sum;
	private BigDecimal avg;
	private BigDecimal max;
	private BigDecimal min;
	private long count;

}
