package ch.hftm.bank.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.NoSuchEJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hftm.bank.business.AccountManagerRemote;
import ch.hftm.bank.business.AccountServiceRemote;
import ch.hftm.bank.exception.AccountNotFoundException;

public class AccountManagerIT {

	private static final String ACCOUNT_SERVICE_NAME = "java:global/bank04_src/AccountService";
	private static final String ACCOUNT_MANAGER_NAME = "java:global/bank04_src/AccountManager";
	private static Context jndiContext;
	private AccountServiceRemote accountService;
	private AccountManagerRemote accountManager;
	private Integer accountNr;
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
		accountNr = accountService.openAccount(balance);
		accountManager = (AccountManagerRemote) jndiContext.lookup(ACCOUNT_MANAGER_NAME);
	}

	@Test
	public void testLogin() throws Exception {
		try {
			accountManager.login(0);
			fail("AccountNotFoundException expected");
		} catch (AccountNotFoundException e) {
		}
		accountManager.login(accountNr);
		accountManager.getBalance();
		accountManager.logout();
	}

	@Test
	public void testDeposit() throws Exception {
		accountManager.login(accountNr);
		accountManager.deposit(amount);
		BigDecimal newBalance = accountManager.getBalance();
		assertEquals(balance.add(amount), newBalance);
		accountManager.logout();
	}

	@Test
	public void testWithdraw() throws Exception {
		accountManager.login(accountNr);
		accountManager.withdraw(amount);
		BigDecimal newBalance = accountManager.getBalance();
		assertEquals(balance.subtract(amount), newBalance);
		accountManager.logout();
	}

	@Test
	public void testLogout() throws Exception {
		accountManager.login(accountNr);
		accountManager.logout();
		try {
			accountManager.getBalance();
			fail("NoSuchEJBException expected");
		} catch (NoSuchEJBException e) {
		}
	}

	@Test
	public void testConcurrentAccess() throws Exception {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					AccountManagerRemote accountManager =
							(AccountManagerRemote) jndiContext.lookup(ACCOUNT_MANAGER_NAME);
					accountManager.login(accountNr);
					accountManager.deposit(amount);
					accountManager.logout();
				} catch (Exception e) {
				}
			}
		};
		thread.start();
		accountManager.login(accountNr);
		accountManager.withdraw(amount);
		accountManager.logout();
		thread.join();
		BigDecimal newBalance = accountService.getBalance(accountNr);
		assertEquals(balance, newBalance);
	}
}
