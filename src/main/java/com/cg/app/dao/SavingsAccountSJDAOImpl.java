package com.cg.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.AccountNotFoundException;
import com.cg.app.rowmapper.SavingsAcountRowMapper;
import com.cg.app.validation.Validation;

@Repository
@Primary
public class SavingsAccountSJDAOImpl implements SavingsAccountDAO{
	
	@Autowired
	private JdbcTemplate template; 
	private Logger logger = Logger.getLogger(SavingsAccountSJDAOImpl.class.getName());
	
	public SavingsAccount createNewAccount(SavingsAccount account) {
		template.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)", new Object[]{account.getBankAccount().getAccountNumber(),
				account.getBankAccount().getAccountHolderName(),
				account.getBankAccount().getAccountBalance(),
				account.isSalary(),
				null,
				"SA"});
		return account;
	}

	
	public double checkCurrentBalance(int accountNumber)throws AccountNotFoundException{
		return template.queryForObject("SELECT account_bal FROM account where account_id=?", new Object[] {accountNumber},Double.class);
	}

	
	public SavingsAccount getAccountById(int accountNumber)throws  AccountNotFoundException {
		return template.queryForObject("SELECT * FROM account where account_id=?", new Object[] {accountNumber},new SavingsAcountRowMapper());
	}

	
	public SavingsAccount deleteAccount(int accountNumber)  {
		 template.update("DELETE FROM account where account_id=?",new Object[] {accountNumber});
		 return null;
	}

	
	public List<SavingsAccount> getAllSavingsAccount(){
		return template.query("SELECT * FROM ACCOUNT",new SavingsAcountRowMapper());
	}

	
	public void updateBalance(int accountNumber, double currentBalance)  {
		 template.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",new Object[] {currentBalance,accountNumber});

	}

	
	public SavingsAccount searchAccountByAccountHoldername(String accountHolderName)throws AccountNotFoundException {
		return template.queryForObject("SELECT * FROM account where account_hn=?",new Object[] {accountHolderName},new SavingsAcountRowMapper());
	}


	public List<SavingsAccount> searchAccountByAccountBalance(double minimumBalance, double maximumBalance){
		return template.query("SELECT * FROM account WHERE account_bal BETWEEN ? AND ?",new Object[] {minimumBalance,maximumBalance},new SavingsAcountRowMapper());
	}

	
	public SavingsAccount updateAccount(SavingsAccount account)throws  AccountNotFoundException {
	 template.update("UPDATE account SET salary=?,account_hn=?  WHERE account_id=?",new Object[] {account.isSalary(),
			 account.getBankAccount().getAccountHolderName(),
			 account.getBankAccount().getAccountNumber()}
			);
	 return getAccountById(account.getBankAccount().getAccountNumber());
	}

	
	public List<SavingsAccount> sort(int choice, int sortBy)  {
		String query = "";
		switch (choice) {
		case 1:
			if (sortBy == 1)
				query = "SELECT * FROM account ORDER BY account_id";
			else
				query = "SELECT * FROM account ORDER BY account_id DESC";
			break;
		case 2:
			if (sortBy == 1)
				query = "SELECT * FROM account ORDER BY account_hn";
			else
				query = "SELECT * FROM account ORDER BY account_hn DESC";
			break;
		case 3:
			if (sortBy == 1)
				query = "SELECT * FROM account ORDER BY account_bal";
			else
				query = "SELECT * FROM account ORDER BY account_bal DESC";
			break;
		}
		
		return template.query(query, new SavingsAcountRowMapper());
	}

}
