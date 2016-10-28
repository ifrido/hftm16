package ch.hftm.bank.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hftm.bank.exception.AccountNotFoundException;
import ch.hftm.bank.exception.WithdrawLimitExceededException;
import ch.hftm.bank.model.Account;

@Stateful (name = "AccountManager")
public class AccountManagerBean implements AccountManagerRemote {
	
	@PersistenceContext(unitName="bank")
	private EntityManager em;
	@Resource(name="withdrawLimit")
	private String withdrawLimit;
	private Account account;

	@Override
	public void login(Integer accountNr) throws AccountNotFoundException {
		account = em.find(Account.class, accountNr);

		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
	}

	@Override
	public BigDecimal getBalance() throws AccountNotFoundException {
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		
		return account.getBalance();
	}

	@Override
	public void deposit(BigDecimal amount) throws AccountNotFoundException {
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		
		account.deposit(amount);
	}

	@Override
	public void withdraw(BigDecimal amount)
			throws AccountNotFoundException, WithdrawLimitExceededException, ParseException {
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		
		DecimalFormat df = new DecimalFormat("");
		df.setParseBigDecimal(true);
		BigDecimal wl = (BigDecimal) df.parse(withdrawLimit);
		
		if (amount.compareTo(wl) == 1) {
			throw new WithdrawLimitExceededException();
		}
		
		account.withdraw(amount);
	}

	@Override
	@Remove
	public void logout() {
		account = null;
	}

}
