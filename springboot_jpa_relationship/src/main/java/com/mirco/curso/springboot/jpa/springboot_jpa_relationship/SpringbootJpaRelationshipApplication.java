package com.mirco.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.entities.Adress;
import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		removeOneToManyInvoiceBidirectionalFindById();
	}

	@Transactional
	public void removeOneToManyInvoiceBidirectionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(2l);

		optionalClient.ifPresent(client -> {

			Invoice invoice1 = new Invoice("compras de peruanos", 200l);
			Invoice invoice2 = new Invoice("1 cocacola", 2500l);
	
			client.addInvoice(invoice1);
			client.addInvoice(invoice2);
	
			clientRepository.save(client);
			System.out.println("====================");
			System.out.println(client);
		});

		Optional<Client> optionalClientDb = clientRepository.findOne(2l);

		optionalClientDb.ifPresent(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2l);

			invoiceOptional.ifPresent(invoice -> {
				client.getInvoices().remove(invoice);
				invoice.setClient(null);

				clientRepository.save(client);
				System.out.println(client);
			});
		});

	}

	@Transactional
	public void oneToManyInvoiceBidirectionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(2l);

		optionalClient.ifPresent(client -> {

			Invoice invoice1 = new Invoice("compras de peruanos", 200l);
			Invoice invoice2 = new Invoice("1 cocacola", 2500l);
	
			client.addInvoice(invoice1);
			client.addInvoice(invoice2);
	
			clientRepository.save(client);
			System.out.println("====================");
			System.out.println(client);
		});

	}

	@Transactional
	public void oneToManyInvoiceBidirectional() {
		Client client = new Client("Fran" , "Moras");

		Invoice invoice1 = new Invoice("compras de peruanos", 200l);
		Invoice invoice2 = new Invoice("1 cocacola", 2500l);

		
		client.addInvoice(invoice1);
		client.addInvoice(invoice2);

		clientRepository.save(client);
		System.out.println("====================");
		System.out.println(client);
	}

	@Transactional
	public void removeFindById(){
		Optional<Client> optionalClient = clientRepository.findById(4l);
		optionalClient.ifPresent(client-> {

			Adress adress = new Adress( "El vergel" , 1234);
			Adress adress2 = new Adress("El pastal" , 4321);

			client.setAdresses(Arrays.asList(adress,adress2));

			clientRepository.save(client);


			Optional<Client> optionalCLient2 = clientRepository.findOneWithAddresses(4l);

			optionalCLient2.ifPresent(c -> {
				c.getAdresses().remove(adress);
				clientRepository.save(c);
				System.out.println(c);
			});


		});
	}

	@Transactional
	public void removeAdress() {
		Client client = new Client("Fran" , "Moras");

		Adress adress = new Adress( "El vergel" , 1234);

		Adress adress2 = new Adress("El pastal" , 4321);

		client.getAdresses().add(adress);
		client.getAdresses().add(adress2);

		clientRepository.save(client);
		System.out.println(client);

		Optional<Client> optionalCLient = clientRepository.findById(4l);

		optionalCLient.ifPresent(c -> {
			c.getAdresses().remove(adress);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void onteToManyFindById(){
		Optional<Client> optionalClient = clientRepository.findById(1l);
		optionalClient.ifPresent(client-> {

			Adress adress = new Adress( "El vergel" , 1234);
			Adress adress2 = new Adress("El pastal" , 4321);

			client.setAdresses(Arrays.asList(adress,adress2));

			clientRepository.save(client);

		});
	}


	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran" , "Moras");

		Adress adress = new Adress( "El vergel" , 1234);
		Adress adress2 = new Adress("El pastal" , 4321);

		client.getAdresses().add(adress);
		client.getAdresses().add(adress2);

		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {
		Client client = new Client("Jhon" , "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000l);
		invoice.setClient(client);

		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByID() {
		Optional<Client> optionalClient = clientRepository.findById(1l);

		if (optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000l);
			invoice.setClient(client);
	
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		} 


	}
}
