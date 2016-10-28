package org.example.client;

import java.util.List;
import java.util.Scanner;

import javax.ws.rs.NotFoundException;
import javax.xml.ws.BindingProvider;

import org.example.model.Customer;

public class CustomerBaseClient {

	private static CustomerBase customerBase;
	private static Scanner scanner;

	public static void main(String[] args) {
		if (args.length > 0) {
			customerBase = new CustomerBaseRestProxy(args[0]);
		} else {
			customerBase = new CustomerBaseRestProxy();
		}
		if (args.length > 0) {
			((BindingProvider) customerBase).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, args[0]);
		}
		scanner = new Scanner(System.in);
		while (true) {
			System.out.println("0 Test Service");
			System.out.println("1 List customers");
			System.out.println("2 Find customer");
			System.out.println("3 Add customer");
			System.out.println("4 Exit");
			System.out.print("> ");
			try {
				int action = Integer.parseInt(scanner.nextLine());
				if (action == 0) {
					testService();
				} else if (action == 1) {
					listCustomers();
				} else if (action == 2) {
					findCustomer();
				} else if (action == 3) {
					addCustomer();
				} else if (action == 4) {
					System.exit(0);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid input");
			} catch (Exception e) {
				System.err.println("Unexpected system error");
			}
			System.out.println();
		}
	}
		
		
		
	private static void testService() {
		String response = customerBase.testService();
		System.out.println(response);
	}

	private static void listCustomers() {
		List<Customer> customers = customerBase.list();
		for (Customer customer : customers) {
			System.out.println();
			System.out.println("Name:  " + customer.getFirstName());
			System.out.println("Phone: " + customer.getLastName());
		}
	}

	private static void findCustomer() {
		System.out.print("Index:  ");
		String index = scanner.nextLine();

		try {
			Customer customer = customerBase.find(index);
			System.out.println("FirstName:  " + customer.getFirstName());
			System.out.println("LastName: " + customer.getLastName());
		} catch (NotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void addCustomer() {
		Customer customer = new Customer();
		System.out.print("FirstName:  ");
		customer.setFirstName(scanner.nextLine());
		System.out.print("LastName: ");
		customer.setLastName(scanner.nextLine());

		customerBase.add(customer);
		System.out.println("Customer added");
	}

}
