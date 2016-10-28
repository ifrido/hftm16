package ch.hftm.bank.business;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ch.hftm.bank.model.Account;

@Singleton (name = "InterestService")
public class InterestServiceBean implements InterestServiceRemote {

	@PersistenceContext(unitName="bank")
	private EntityManager em;
	@Resource(name = "interestRate")
	private String interestRate;
	@Resource
	private TimerService timerService;
	
	@Override
	public void schedulePayments(Date expiration) {
		timerService.createSingleActionTimer(expiration, new TimerConfig());
	}

	@Override
	public void schedulePayments(long interval) {
		timerService.createSingleActionTimer(interval, new TimerConfig());
	}

	@Override
	public void schedulePayments(ScheduleExpression schedule) {
		timerService.createCalendarTimer(schedule, new TimerConfig());
	}
	
	@Timeout
	public void payInterests() throws ParseException {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setParseBigDecimal(true);
		BigDecimal ir = (BigDecimal) df.parse(interestRate);
		
		List<Account> accountList = em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
		
		for (Account account : accountList) {
			BigDecimal amount = (account.getBalance().multiply(ir)).setScale(2, RoundingMode.CEILING);
			
			System.out.println("Paying interests (" + amount + ") account " + account.getNr());
			account.deposit(amount);
		}
		
	}

	@Override
	public void cancelPayments() {
		Collection<Timer> timers = timerService.getAllTimers();
		for (Timer timer : timers) {
			timer.cancel();
		}
	}
	
	

}
