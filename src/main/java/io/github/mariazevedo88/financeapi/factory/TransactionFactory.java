package io.github.mariazevedo88.financeapi.factory;

import io.github.mariazevedo88.financeapi.model.Transaction;

/**
 * Interface that provides method for manipulate a transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TransactionFactory {

	Transaction createTransaction(String type);
}
