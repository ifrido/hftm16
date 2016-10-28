package org.example.client;

import java.util.List;
import javax.ws.rs.NotFoundException;
import org.example.model.Customer;

public interface CustomerBase {

	public String testService();

	public List<Customer> list();

	public Customer find(String index) throws NotFoundException;

	public void add(Customer customer);

}
