package ch.hftm.business;

import java.util.List;

import javax.ejb.Remote;

import ch.hftm.model.Contact;

@Remote
public interface ContactBookRemote {

	public void add(Contact contact);

	public Contact find(String name);

	public void update(Contact contact);

	public void remove(String name);
	
	public List<Contact> list();
	

}
