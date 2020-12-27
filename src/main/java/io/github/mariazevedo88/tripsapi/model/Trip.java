package io.github.mariazevedo88.tripsapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.github.mariazevedo88.tripsapi.enumeration.TripTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements the Trip structure.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
	
	private Long id;
	private String orderCode;
	private BigDecimal amount;
	private LocalDateTime initialDate;
	private LocalDateTime finalDate;
	private TripTypeEnum type;
	
	public Trip(TripTypeEnum type){
		this.type = type;
	}

}
