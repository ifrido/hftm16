package ch.hftm.bank.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AccountNotBalancedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 225477704796248988L;
}
