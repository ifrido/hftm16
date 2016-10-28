package ch.hftm.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hftm.model.Contact;

@Stateless
public class ContactBookBean implements ContactBookRemote {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Contact contact) {
		em.persist(contact);
	}

	@Override
	public Contact find(String name) {
		return em.find(Contact.class, name);
	}

	@Override
	public void update(Contact contact) {
		em.merge(contact);
	}

	@Override
	public void remove(String name) {
		Contact contact = em.getReference(Contact.class, name);
		em.refresh(contact);
	}

	@Override
	public List<Contact> list() {
		Query q = em.createQuery("SELECT C from Contact c");
		return q.getResultList();
	}
	

}
