package ch.hftm.example;

import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hftm.business.ContactBookRemote;
import ch.hftm.model.Contact;
import static org.junit.Assert.*;

public class ContactBookIT {


	private static final String JNDI_NAME = "java:global/contactbook/ContactBookBean";
	private static Context jndiContext;
	private ContactBookRemote contactBook;
	private Contact contact;

	@BeforeClass
	public static void setup() throws NamingException {
		jndiContext = new InitialContext();
	}

	@Before
	public void init() throws NamingException {
		contactBook = (ContactBookRemote) jndiContext.lookup(JNDI_NAME);
		contact = new Contact();
		contact.setName(getRandomName(5));
	}

	@Test
	public void testAdd() throws Exception{
		contactBook.add(contact);
		contact = contactBook.find(contact.getName());
		assertNotNull(contact);
	}

	@Test
	public void testUpdate() throws Exception{
		contactBook.add(contact);
		String email = contact.getName() + "@example.org";
		contact.setEmail(email);
		contactBook.update(contact);
		contact = contactBook.find(contact.getName());
		assertEquals(email, contact.getEmail());
	}

	@Test
	public void testRemove() throws Exception{
		contactBook.add(contact);
		contactBook.remove(contact.getName());
		contact = contactBook.find(contact.getName());
		assertNull(contact);
	}

	private String getRandomName(int length) {
		String name = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			name += (char) ('a' + random.nextInt('z' - 'a'));
		}
		return name;
	}
}
