package org.example.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class CustomerResourceTest {

	private static final String BASE_URI = "http://localhost:8080/rest01/customers/";

	@Test
	public void testCustomerResource() throws Exception {
		System.out.println("*** Create a new Customer ***");

		// Create a new customer
	      String newCustomer = "<customer>"
	              + "<firstName>Bill</firstName>"
	              + "<lastName>Burke</lastName>"
	              + "</customer>";

		URL postUrl = new URL(BASE_URI);
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/xml");
		OutputStream os = connection.getOutputStream();
		os.write(newCustomer.getBytes());
		os.flush();
		connection.disconnect();
		System.out.println("*** !!!! ***");

	}
}
