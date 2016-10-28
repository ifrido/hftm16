package ch.hftm.bank.business;

import java.util.Date;
import javax.ejb.Remote;
import javax.ejb.ScheduleExpression;

@Remote
public interface InterestServiceRemote {

	public void schedulePayments(Date expiration);
	
	public void schedulePayments(long interval);
	
	public void schedulePayments(ScheduleExpression schedule);
	
	public void cancelPayments();
	
}
