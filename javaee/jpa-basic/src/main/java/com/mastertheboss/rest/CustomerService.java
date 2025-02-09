package com.mastertheboss.rest;



import java.util.List;
import java.util.logging.Logger;


import com.mastertheboss.model.Customer;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;





public class CustomerService {


	@PersistenceContext
	EntityManager em;

	@PersistenceUnit
	EntityManagerFactory emf;

	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
	@Transactional
	public void createCustomer(Customer customer) {
		em.persist(customer);
		LOGGER.info("Created Customer "+customer);

	}


	@Transactional
	public void updateCustomer( Customer customer ) {
		Customer customerToUpdate = findCustomerById(customer.getId());
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setAddress(customer.getAddress());
		customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
		LOGGER.info("Updated customer" + customer);
	}


	@Transactional
	public void deleteCustomer(Long customerId) {
		Customer c = findCustomerById(customerId);
		em.remove(c);
		LOGGER.info("Deleted Customer with id" + customerId);
	}


	public Customer findCustomerById(Long id) {
		Customer customer = em.find(Customer.class, id);
		if (customer == null) {
			throw new WebApplicationException("Customer with id of " + id + " does not exist.", 404);
		}
		return customer;
	}


	public List<Customer> findAllCustomers() {
		Query query = em.createQuery("SELECT c FROM Customer c");
		List<Customer> customerList = query.getResultList();
		return customerList;
	}



	public Customer findCustomerByName(String name) {
		Query query = em
				.createQuery("SELECT c FROM Customer c WHERE c.name = :name");
		query.setParameter("name", name);
		Customer customer = (Customer) query.getSingleResult();
		return customer;
	}


}