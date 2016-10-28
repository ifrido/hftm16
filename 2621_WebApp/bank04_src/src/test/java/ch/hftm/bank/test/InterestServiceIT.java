package ch.hftm.bank.test;

import java.util.Date;

import javax.ejb.ScheduleExpression;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hftm.bank.business.InterestServiceRemote;

public class InterestServiceIT {

	private static final String INTEREST_SERVICE_NAME = "java:global/bank04_src/InterestService";
	private static Context jndiContext;
	private InterestServiceRemote interestService;

	@BeforeClass
	public static void setup() throws NamingException {
		jndiContext = new InitialContext();
	}

	@Before
	public void init() throws NamingException {
		interestService = (InterestServiceRemote) jndiContext.lookup(INTEREST_SERVICE_NAME);
	}

	@Test
	public void testSinglePayments() throws Exception {
		Date expiration = new Date();
		expiration.setTime(expiration.getTime() + 60000);
		interestService.schedulePayments(expiration);
	}

	@Test
	public void testIntervalPayments() throws Exception {
		interestService.schedulePayments(60000);
	}

	@Test
	public void testCalendarPayments() throws Exception {
		ScheduleExpression schedule = new ScheduleExpression();
		schedule.hour("*").minute("*");
		interestService.schedulePayments(schedule);
	}

	@Test
	public void testCancelPayments() throws Exception {
		interestService.cancelPayments();
	}
}