package io.github.mariazevedo88.financeapi.factory.impl;

import io.github.mariazevedo88.financeapi.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financeapi.factory.TransactionFactory;
import io.github.mariazevedo88.financeapi.model.Transaction;

/**
 * Factory class for the Transaction entity.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public class TransactionFactoryImpl implements TransactionFactory{

	@Override
	public Transaction createTransaction(String type) {
		if(TransactionTypeEnum.MONEY.getValue().equals(type)) {
			return new Transaction(TransactionTypeEnum.MONEY);
		}
		return new Transaction(TransactionTypeEnum.CARD);
	}

}
