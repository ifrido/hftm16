package ch.hftm.notenrechner.business;

import javax.ejb.Local;

@Local
public interface ReportingServiceLocal {

	public void scheduleReport(long interval);

	public void cancelReports();
	
	public String testService();

}
