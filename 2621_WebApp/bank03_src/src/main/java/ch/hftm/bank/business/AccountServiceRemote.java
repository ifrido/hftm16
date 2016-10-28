package ch.hftm.bank.business;

import java.math.BigDecimal;

import javax.ejb.Remote;

import ch.hftm.bank.exception.AccountNotBalancedException;
import ch.hftm.bank.exception.AccountNotFoundException;
import ch.hftm.bank.exception.WithdrawLimitExceededException;

@Remote
public interface AccountServiceRemote {

	public Integer openAccount(BigDecimal initialBalance);

	public BigDecimal getBalance(Integer accountNr)
			throws AccountNotFoundException;

	public void deposit(Integer accountNr, BigDecimal amount)
			throws AccountNotFoundException;

	public void withdraw(Integer accountNr, BigDecimal amount)
			throws AccountNotFoundException, WithdrawLimitExceededException;

	public void closeAccount(Integer accountNr)
			throws AccountNotFoundException, AccountNotBalancedException;
}
