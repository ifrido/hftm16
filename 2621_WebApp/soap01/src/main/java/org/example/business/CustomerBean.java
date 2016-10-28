package org.example.business;

import java.util.List;

import javax.ejb.Stateless;

import org.example.exception.MissingSearchCriteriaException;
import org.example.exception.NotFoundException;
import org.example.model.Customer;

@Stateless
public class CustomerBean {

	private CustomerDB customerDB = CustomerDB.getInstance();

	public CustomerBean() {

	}

	public Customer addCustomer(Customer customer) {
		System.out.println("CustomerService.addCustomer");
		return customerDB.addCustomer(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerDB.getAllCustomers();
	}

	public Customer getCustomer(int index) {
		return customerDB.getCustomer(index);
	}

	public List<Customer> searchCustomers(String firstName, String lastName)
			throws MissingSearchCriteriaException, NotFoundException {
		List<Customer> customers;
		try {
			customers = customerDB.findCustomer(firstName, lastName);
			if (customers.isEmpty()) {
				throw new NotFoundException("No Customer found: ", firstName);
			}
		} catch (MissingSearchCriteriaException e) {
			throw new MissingSearchCriteriaException();
		}
		return customers;
	}

}
