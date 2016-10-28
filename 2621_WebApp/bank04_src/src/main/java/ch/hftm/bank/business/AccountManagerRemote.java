package ch.hftm.bank.business;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.ejb.Remote;

import ch.hftm.bank.exception.AccountNotFoundException;
import ch.hftm.bank.exception.WithdrawLimitExceededException;

@Remote
public interface AccountManagerRemote {

	public void login(Integer accountNr) throws AccountNotFoundException;

	public BigDecimal getBalance() throws AccountNotFoundException;

	public void deposit(BigDecimal amount) throws AccountNotFoundException;

	public void withdraw(BigDecimal amount)
			throws AccountNotFoundException, WithdrawLimitExceededException, ParseException;

	public void logout();

}
