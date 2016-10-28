package ch.hftm.bank.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Integer nr;
	@Column(precision = 12, scale = 2)
	private BigDecimal balance;

	public Account() {
	}

	public Account(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getNr() {
		return nr;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void deposit(BigDecimal amount) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		balance = balance.add(amount);
	}

	public void withdraw(BigDecimal amount) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		balance = balance.subtract(amount);
	}
}
