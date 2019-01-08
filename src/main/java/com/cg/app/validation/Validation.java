
package com.cg.app.validation;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cg.app.pojo.account.SavingsAccount;
import com.cg.app.pojo.exception.InsufficientFundsException;
import com.cg.app.pojo.exception.InvalidInputException;

@Component
@Aspect
public class Validation {
	private Logger logger = Logger.getLogger(Validation.class.getName());

	@Around("execution(* com.cg.app.service.SavingsAccountServiceImpl.withdraw(..))")
	public void validateWithdraw(ProceedingJoinPoint pjp) throws Throwable {
		Object[] parameters = pjp.getArgs();
		SavingsAccount savingsAccount = (SavingsAccount) parameters[0];
		double currentBalance = savingsAccount.getBankAccount().getAccountBalance();
		if ((Double) parameters[1] > 0 && currentBalance >= (Double) parameters[1]) {
				pjp.proceed();
				logger.info("Withdraw successfull!");
		} else
			logger.info("Withdraw failed!");
			 //throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
	}

	@Around("execution(* com.cg.app.service.SavingsAccountServiceImpl.deposit(..))")
	public void validateDeposite(ProceedingJoinPoint pjp) throws Throwable {
		Object[] parameters = pjp.getArgs();
		if ((Double) parameters[1] > 0) {
			pjp.proceed();
			logger.info("Deposit successfull!");
		}

		else
			logger.info("Deposit failed!");
			//throw new InvalidInputException("Invalid Input Amount!");
	}
}
