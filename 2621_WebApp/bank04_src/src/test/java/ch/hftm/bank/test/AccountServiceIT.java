package ch.hftm.bank.test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hftm.bank.business.AccountServiceRemote;
import ch.hftm.bank.exception.AccountNotBalancedException;
import ch.hftm.bank.exception.AccountNotFoundException;
import ch.hftm.bank.exception.WithdrawLimitExceededException;
import static org.junit.Assert.*;

public class AccountServiceIT {

	private static final String ACCOUNT_SERVICE_NAME = "java:global/bank04_src/AccountService";
	private static final Integer WITHDRAW_LIMIT = 1000;
	private static Context jndiContext;
	private AccountServiceRemote accountService;
	private BigDecimal balance;
	private BigDecimal amount;

	@BeforeClass
	public static void setup() throws NamingException {
		Logger.getLogger("").setLevel(Level.WARNING);
		jndiContext = new InitialContext();
	}

	@Before
	public void init() throws NamingException {
		balance = BigDecimal.valueOf(new Random().nextInt(100000), 2);
		amount = BigDecimal.valueOf(new Random().nextInt(1000), 2);
		accountService = (AccountServiceRemote) jndiContext.lookup(ACCOUNT_SERVICE_NAME);
	}



	@Test
	public void testDeposit() throws Exception {
		Integer accountNr = accountService.openAccount(balance);
		accountService.deposit(accountNr, amount);
		BigDecimal newBalance = accountService.getBalance(accountNr);
		assertEquals(balance.add(amount), newBalance);
	}

	@Test
	public void testWithdraw() throws Exception {
		Integer accountNr = accountService.openAccount(balance);
		
		accountService.withdraw(accountNr, amount);
		
		BigDecimal newBalance = accountService.getBalance(accountNr);
		assertEquals(balance.subtract(amount), newBalance);
		
		try {
			accountService.withdraw(accountNr, BigDecimal.valueOf(WITHDRAW_LIMIT + 1));
			fail("WithdrawLimitExceededException expected");
		} catch (WithdrawLimitExceededException e) {
		}
	}

	@Test
	public void testClose() throws Exception {
		Integer accountNr = accountService.openAccount(balance);
		try {
			accountService.closeAccount(accountNr);
			fail("AccountNotBalancedException expected");
		} catch (AccountNotBalancedException e) {
		}
		accountService.withdraw(accountNr, balance);
		accountService.closeAccount(accountNr);
		try {
			accountService.getBalance(accountNr);
			fail("AccountNotFoundException expected");
		} catch (AccountNotFoundException e) {
		}
	}

//	@Test
//	public void testConcurrentAccess() throws Exception {
//		final Integer accountNr = accountService.openAccount(balance);
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
//				try {
//					AccountServiceRemote accountService =
//							(AccountServiceRemote) jndiContext.lookup(ACCOUNT_SERVICE_NAME);
//					accountService.deposit(accountNr, amount);
//				} catch (Exception e) {
//				}
//			}
//		};
//		thread.start();
//		accountService.withdraw(accountNr, amount);
//		thread.join();
//		BigDecimal newBalance = accountService.getBalance(accountNr);
//		assertEquals(balance, newBalance);
//	}
	
}
