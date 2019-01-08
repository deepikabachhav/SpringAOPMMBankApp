package com.cg.app.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.AccountNotFoundException;
import com.cg.app.service.SavingsAccountService;

@Component
public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	@Autowired
	private  SavingsAccountService savingsAccountService ;

	public  void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private   void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;
		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			try {
				deposit();
			} catch (AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 7:
			try {
				fundTransfer();
			} catch (AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 8:
			checkCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortMenu();
			break;
		case 11:
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private  void searchAccount() {
		System.out.println("Search Account By: ");
		System.out.println("1. Account Number: ");
		System.out.println("2. Account Holder Name: ");
		System.out.println("3. Account Balance: ");
		System.out.println("Make your choice: ");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter Account Number you want to search: ");
			int accountNumber = scanner.nextInt();
			try {
				SavingsAccount searchByAccountNumber = savingsAccountService.getAccountById(accountNumber);
				System.out.println(searchByAccountNumber);
			} catch ( AccountNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		case 2:
			System.out.println("Enter Account Holder Name you want to search: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			try {
				SavingsAccount searchByAccountHolderName = savingsAccountService
						.searchAccountByAccountHoldername(accountHolderName);
				System.out.println(searchByAccountHolderName);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter the Range of Account Balance: ");
			System.out.println("Enter Minimum Balance: ");
			double minimumBalance = scanner.nextDouble();
			System.out.println("Enter Maximum Balance: ");
			double maximumBalance = scanner.nextDouble();
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.searchAccountByAccountBalance(minimumBalance, maximumBalance);
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (Exception e1) {
				e1.printStackTrace();

			}
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}

	private  void updateAccount() {
		System.out.println("Enter Account Number whose details you want to update: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount account = null;
		try {
			account = savingsAccountService.getAccountById(accountNumber);
		} catch ( AccountNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Which Details you want to update: ");
		System.out.println("1. Account Holder Name: ");
		System.out.println("2. Is salaried or Non-salaried account: ");
		System.out.println("3. update both Account Holder Name and salary: ");
		System.out.println("Make your choice: ");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter new Account Holder Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			account.getBankAccount().setAccountHolderName(accountHolderName);
			try {
				SavingsAccount updateAccountByName = savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByName);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false : true;
			account.setSalary(salary);
			try {
				SavingsAccount updateAccountByisSalary = savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByisSalary);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter new Account Holder Name: ");
			String newaccountHolderName = scanner.nextLine();
			newaccountHolderName = scanner.nextLine();
			System.out.println("Salaried?(y/n): ");
			boolean newsalary = scanner.next().equalsIgnoreCase("n") ? false : true;
			account.getBankAccount().setAccountHolderName(newaccountHolderName);
			account.setSalary(newsalary);
			try {
				SavingsAccount updateAccountByBoth = savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByBoth);
			} catch ( AccountNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}

	private  void checkCurrentBalance() {
		System.out.println("Enter Account Number whose account balance to be checked: ");
		int accountNumber = scanner.nextInt();
		try {
			double checkAccount = savingsAccountService.checkCurrentBalance(accountNumber);
			System.out.println(" Your SavingsAccount current balance is :" + checkAccount);
		} catch ( AccountNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private  void closeAccount() {
		System.out.println("Enter Account Number to be closed: ");
		int accountNumber = scanner.nextInt();
		try {
			SavingsAccount closeAccount = savingsAccountService.deleteAccount(accountNumber);
		} catch ( AccountNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private  void fundTransfer() throws AccountNotFoundException {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		
	}

	private  void deposit() throws AccountNotFoundException {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);	
	}

	private  void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
		} catch ( AccountNotFoundException e) {
			e.printStackTrace();
		}
	}

	private  void sortMenu() {
		int sortBy = 0;
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from sorting");
			int choice = scanner.nextInt();
			if (choice != 4) {
				System.out.println("Sort By: ");
				System.out.println("1.Ascending order: ");
				System.out.println("2.Descending order: ");
				System.out.println("Make your choice: ");
				sortBy = scanner.nextInt();
				switch (choice) {
				case 1:

						List<SavingsAccount> sortByAccountNumber = savingsAccountService.sort(choice, sortBy);
						System.out.println(sortByAccountNumber);
				
					break;
				case 2:

						List<SavingsAccount> sortByAccountHolderName = savingsAccountService.sort(choice, sortBy);
						System.out.println(sortByAccountHolderName);
					break;
				case 3:

						List<SavingsAccount> sortByAccountBalance = savingsAccountService.sort(choice, sortBy);
						System.out.println(sortByAccountBalance);
					break;
				}
			} else
				break;
		} while (true);
	}

	private  void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
	}

	private  void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false : true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private  void createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
	}

}
