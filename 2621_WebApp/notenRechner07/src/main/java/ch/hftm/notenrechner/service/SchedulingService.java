package ch.hftm.notenrechner.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ch.hftm.notenrechner.business.ReportingServiceLocal;

@Stateless
@Path("/scheduling")
public class SchedulingService {

	@EJB(name = "ReportingService")
	ReportingServiceLocal reportingService;

	@GET
	@Path("createNew")
	public Response schedule(@QueryParam("interval") long interval) {
		reportingService.scheduleReport(interval);
		return Response.status(200).build();
	}

	@GET
	@Path("cancelAll")
	public Response cancel() {
		reportingService.cancelReports();
		return Response.status(200).build();
	}
	
	@GET
	@Path("testService")
	public String testService() {
		return reportingService.testService();
	}


}
