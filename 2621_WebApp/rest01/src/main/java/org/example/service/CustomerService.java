package org.example.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.example.model.Customer;

@Stateless
@Path("/customers")
public class CustomerService {
	CustomerDB cdb = CustomerDB.getInstance();
	
	@GET
	@Path("test")
	@Produces("application/xml")
	public String Hallo() {
		return "Test ok";
	}
	
	@GET
	@Path("getAllCustomers")
	@Produces("application/json")
	public List<Customer> getAllCustomers() {
		return cdb.getAllCustomers();
	}
	
	@GET
	@Path("getCustomerPP/{index}")
	@Produces("application/xml")
	public Customer getCustomerPP(@PathParam("index") int index) {
		return cdb.getCustomer(index);
	}
	
	@GET
	@Path("getCustomerHP")
	@Produces("application/xml")
	public Customer getCustomerHP(@QueryParam("index") int index) {
		return cdb.getCustomer(index);
	}
	
	@GET
	@Path("getCustomerQP")
	@Produces("application/xml")
	public Customer getCustomerQP(@HeaderParam("index") int index) {
		return cdb.getCustomer(index);
	}	
	
	@POST
	@Path("addCustomer")
	@Produces("application/xml")
	public Customer addCustomer(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		return cdb.addCustomer(customer);
	}
	
	@POST
	@Path("addCustomerObj")
	@Produces("application/xml")
	@Consumes("application/xml")
	public Customer addCustomerObj(Customer customer) {
		return cdb.addCustomer(customer);
	}
	
}