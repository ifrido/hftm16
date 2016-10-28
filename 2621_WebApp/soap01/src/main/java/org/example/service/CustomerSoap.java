package org.example.service;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.example.business.CustomerBean;
import org.example.model.Customer;

@WebService(name= "Customer")
public class CustomerSoap {
	
	@EJB CustomerBean customerBean;
	
	@WebMethod(operationName = "list")
    @WebResult(name = "customer")
    public List<Customer> list() {
       return customerBean.getAllCustomers();
	}
}
