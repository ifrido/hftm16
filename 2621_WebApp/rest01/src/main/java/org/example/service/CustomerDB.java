package org.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.example.exception.MissingSearchCriteriaException;
import org.example.model.Customer;

@Startup
@Singleton(name = "CustomerDB")
public class CustomerDB {
	private List<Customer> customers = new ArrayList<Customer>();
	private static CustomerDB instance;

	public CustomerDB() {

		Customer a = new Customer();
		a.setFirstName("Andrea");
		a.setLastName("A");
		customers.add(a);

		Customer b = new Customer();
		b.setFirstName("Berta");
		b.setLastName("B");
		customers.add(b);
	}

	public Customer addCustomer(Customer customer) {
		customers.add(customer);
		System.out.println("CustomerDB.addCustomer");
		return customer;
	}

	public List<Customer> getAllCustomers() {
		return customers;
	}

	public Customer getCustomer(int index) {
		return customers.get(index);
	}

	public List<Customer> findCustomer(String firstName, String lastName)
			throws MissingSearchCriteriaException {
		if ((firstName == null || firstName.isEmpty())
				&& (lastName == null || lastName.isEmpty())) {
			throw new MissingSearchCriteriaException();
		}

		firstName = firstName.toLowerCase();
		lastName = lastName.toLowerCase();;
		List<Customer> searchResult = new ArrayList<Customer>();

		for (Customer customer : customers) {
			if (customer.getFirstName().toLowerCase().indexOf(firstName) >= 0
					&& customer.getLastName().toLowerCase().indexOf(lastName) >= 0) {
				searchResult.add(customer);
			}
		}
		return searchResult;
	}

	public static CustomerDB getInstance() {
		if (instance == null) {
			instance = new CustomerDB();
		}
		return instance;
	}

}
