package ch.hftm.bank.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hftm.bank.exception.AccountNotBalancedException;
import ch.hftm.bank.exception.AccountNotFoundException;
import ch.hftm.bank.exception.WithdrawLimitExceededException;
import ch.hftm.bank.model.Account;

@Stateless(name = "AccountService")
public class AccountServiceBean implements AccountServiceRemote {
	
	@PersistenceContext(unitName="bank")
	private EntityManager em;
	@Resource(name = "withdrawLimit")
	private String withdrawLimit;
	
	@Override
	public Integer openAccount(BigDecimal initialBalance) {
		Account account = new Account(initialBalance);
		em.persist(account);
		em.flush();
		return account.getNr();
	}

	@Override
	public BigDecimal getBalance(Integer accountNr) throws AccountNotFoundException {
		Account account = em.find(Account.class, accountNr);
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		em.refresh(account);
		return account.getBalance();
	}

	@Override
	public void deposit(Integer accountNr, BigDecimal amount) throws AccountNotFoundException {
		Account account = em.find(Account.class, accountNr);
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		
		account.deposit(amount);
	}

	@Override
	public void withdraw(Integer accountNr, BigDecimal amount) throws AccountNotFoundException, WithdrawLimitExceededException, ParseException {
		Account account = em.find(Account.class, accountNr);
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
		//em.refresh(account);
	}

	@Override
	public void closeAccount(Integer accountNr) throws AccountNotFoundException, AccountNotBalancedException {
		Account account = em.find(Account.class, accountNr);
		
		if (!(account instanceof Account)) {
			throw new AccountNotFoundException();
		}
		
		BigDecimal ab = account.getBalance();
		BigDecimal zd = BigDecimal.ZERO;
		
		int c = ab.compareTo(zd);
		
		if(c == 0){
			em.remove(account);
		}else{
			throw new AccountNotBalancedException();
		}
	}

}