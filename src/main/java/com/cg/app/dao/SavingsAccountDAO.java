package com.cg.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.AccountNotFoundException;

public interface SavingsAccountDAO {

	SavingsAccount createNewAccount(SavingsAccount account);

	double checkCurrentBalance(int accountNumber) throws AccountNotFoundException;

	SavingsAccount getAccountById(int accountNumber)throws AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber);

	List<SavingsAccount> getAllSavingsAccount() ;

	void updateBalance(int accountNumber, double currentBalance);

	SavingsAccount searchAccountByAccountHoldername(String accountHolderName)throws AccountNotFoundException;

	List<SavingsAccount> searchAccountByAccountBalance(double minimumBalance, double maximumBalance);

	SavingsAccount updateAccount(SavingsAccount account)throws AccountNotFoundException;

	List<SavingsAccount> sort(int choice, int sortBy);
}
