package br.com.equals.financeapi.factory;

import br.com.equals.financeapi.model.Transaction;

/**
 * Interface that provides method for manipulate a transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TransactionFactory {

	Transaction createTransaction(String type);
}
