package ch.hftm.notenrechner.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.hftm.notenrechner.business.GradeCalculatorDB;
import ch.hftm.notenrechner.exception.ForbiddenException;
import ch.hftm.notenrechner.model.Student;

@Stateless
@Path("/student")
public class StudentService {

	public static final String ROLE_STUDENT = "student";
	public static final String ROLE_TEACHER = "teacher";



	@Context
	private HttpServletRequest request;
	
	@EJB
	private GradeCalculatorDB gcdb;

	private void handleLogin(int userID, int pin) throws ForbiddenException, ServletException {
		if (request.getRemoteUser() == null) {
			if (userID == 0 || pin == 0) {
				throw new ForbiddenException("Username and/or password not specified", Status.UNAUTHORIZED);
			}
			request.login(String.valueOf(userID), String.valueOf(pin));
		}
	}
	
	@GET
	@Path("getAllStudents")
	@Produces("application/xml")
	public List<Student> getAllStudents(@HeaderParam("userID") int userID, @HeaderParam("pin") int pin) throws ForbiddenException, ServletException {
		handleLogin(userID, pin);
		if (request.isUserInRole(ROLE_TEACHER)) {
			return gcdb.getAllStudents();
		} else {
			throw new ForbiddenException("Access not granted for user: " + userID , Status.FORBIDDEN);
		}
		
	}
	
	@GET
	@Path("getStudent")
	@Produces({"application/xml", "application/json"})
	public Student getStudent(@HeaderParam("userID") int userID, @HeaderParam("pin") int pin, @QueryParam("studentID") int studentID) throws ForbiddenException, ServletException {
		handleLogin(userID, pin);
		if (request.isUserInRole(ROLE_TEACHER)) {
			return gcdb.getStudent(studentID);
		} else if (request.isUserInRole(ROLE_STUDENT) && userID == studentID ) {
			return gcdb.getStudent(studentID);
		} else {
			throw new ForbiddenException("You can only get information about zour own user iwth the ID: " + userID, Status.FORBIDDEN);
		}
	}
	
	@POST
	@Path("addStudent")
	@Produces({"application/xml", "application/json"})
	@Consumes("application/xml")
	public Response addStudent(Student student) {
		gcdb.addStudent(student);
		return Response.status(201).entity(student).build();
	}

}
