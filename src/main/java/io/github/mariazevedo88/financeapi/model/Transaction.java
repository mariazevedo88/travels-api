package io.github.mariazevedo88.financeapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.github.mariazevedo88.financeapi.enumeration.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements the Transaction structure.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
	
	private Long id;
	private String nsu;
	private String autorizationNumber;
	private BigDecimal amount;
	private LocalDateTime transactionDate;
	private TransactionTypeEnum type;
	
	public Transaction(TransactionTypeEnum type){
		this.type = type;
	}

}
