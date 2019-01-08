package com.cg.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.app.dao.SavingsAccountDAO;
import com.cg.app.factory.AccountFactory;
import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.AccountNotFoundException;
import com.cg.app.pojo.exception.InsufficientFundsException;
import com.cg.app.pojo.exception.InvalidInputException;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;
	
	
	public SavingsAccountServiceImpl( SavingsAccountDAO savingsAccountDAO) {
		factory = AccountFactory.getInstance();
		//savingsAccountDAO = new SavingsAccountDAOImpl();
	}

	
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary){
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		return savingsAccountDAO.createNewAccount(account);
	}

	public List<SavingsAccount> getAllSavingsAccount() {
		return savingsAccountDAO.getAllSavingsAccount();
	}
	
	@Transactional
	public void deposit(SavingsAccount account, double amount) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
	}
	
	@Transactional
	public void withdraw(SavingsAccount account, double amount)  {
		double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
	}

	@Transactional
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) {
			withdraw(sender, amount);
			deposit(receiver, amount);	
	}

	public SavingsAccount getAccountById(int accountNumber)throws AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	public SavingsAccount deleteAccount(int accountNumber)throws AccountNotFoundException{
		return savingsAccountDAO.deleteAccount(accountNumber);
	}

	public double checkCurrentBalance(int accountNumber)throws AccountNotFoundException {
		return savingsAccountDAO.checkCurrentBalance(accountNumber);
	}

	public SavingsAccount searchAccountByAccountHoldername(String accountHolderName)throws AccountNotFoundException {
		return savingsAccountDAO.searchAccountByAccountHoldername(accountHolderName);
	}

	public List<SavingsAccount> searchAccountByAccountBalance(double minimumBalance, double maximumBalance) {
		return savingsAccountDAO.searchAccountByAccountBalance(minimumBalance, maximumBalance);
	}

	public SavingsAccount updateAccount(SavingsAccount account)throws AccountNotFoundException {
		return savingsAccountDAO.updateAccount(account);
	}

	public List<SavingsAccount> sort(int choice, int sortBy) {
		return savingsAccountDAO.sort(choice, sortBy);
	}

}
