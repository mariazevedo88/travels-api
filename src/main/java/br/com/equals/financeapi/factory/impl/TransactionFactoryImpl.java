package br.com.equals.financeapi.factory.impl;

import br.com.equals.financeapi.enumeration.TransactionTypeEnum;
import br.com.equals.financeapi.factory.TransactionFactory;
import br.com.equals.financeapi.model.Transaction;

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
