package com.cg.app.service;

import java.sql.SQLException;
import java.util.List;

import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary);
			
	double checkCurrentBalance(int accountNumber) throws AccountNotFoundException;

	SavingsAccount getAccountById(int accountNumber)throws AccountNotFoundException;

	SavingsAccount searchAccountByAccountHoldername(String accountHolderName)throws AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber)throws AccountNotFoundException;

	List<SavingsAccount> getAllSavingsAccount();

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount);

	void deposit(SavingsAccount account, double amount);

	void withdraw(SavingsAccount account, double amount);

	List<SavingsAccount> searchAccountByAccountBalance(double minimumBalance, double maximumBalance);

	SavingsAccount updateAccount(SavingsAccount account)
			throws AccountNotFoundException;

	List<SavingsAccount> sort(int choice, int sortBy);
}
