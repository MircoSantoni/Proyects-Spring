package com.mirco.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.mirco.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.mirco.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		update();
	}

	@Transactional(readOnly=true)
	public void personalizedQuerysBetween(){
		System.out.println("========== Consulta between ===========");
		List<Person> persons = repository.findAllBetweenId();
		persons.forEach(System.out::println);

		System.out.println("========== Consulta nombre ===========");
		List<Person> nombres = repository.findAllBetweenNames();
		nombres.forEach(System.out::println);
	}

	@Transactional(readOnly=true)
	public void personalizedQuerys2() {
	System.out.println("========== Consulta por objeto persona y lenguaje de programacion ===========");
	List<Object[]> personRegs = repository.findAllMixPerson();
	
	personRegs.forEach(reg -> {
		System.out.println("Programming language: " + reg[1] + "   Person: " + reg[0]);
	});

	System.out.println("Consulta que puebla y devuelve objeto entittie instancia personalizada");
	List<Person> persons = repository.findAllObjectPersonPersonalized();

	persons.forEach(System.out::println);
	

	System.out.println("Consulta que puebla y devuelve un DTO");
	List<PersonDto> personDtos = repository.findAllPersonDto();
	personDtos.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQuerysDistinct() {

		System.out.println("========== Consultas con nombres de personas ==========");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);
		System.out.println("========== Consultas con nombres unicos ==========");

		List<String> namess = repository.findAllNamesDistinct();
		namess.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQuerysConcatUpperAndLowerCase() {
		System.out.println("Consultas nombres y apellidos de personas");
		List<String> list = repository.findAllFullNameConcat();
		list.forEach(System.out::println);

		List<String> list2 = repository.findAllFullNameConcatUpper();
		list2.forEach(System.out::println);

		List<String> list3 = repository.findAllFullNameConcatLower();
		list3.forEach(System.out::println);
	}
	@Transactional(readOnly=true)
public void personalizedQuerys() {

	Scanner scanner = new Scanner(System.in);

	System.out.println("========== Consulta el nombre por id ===========");
	System.out.println("Ingresa el id hno: ");
	Long id = scanner.nextLong();

	System.out.println("========== Mostrando el nombre ===========");

	String name = repository.getNameById(id);
	System.out.println(name);

	Long idDb = repository.getById(id);
	System.out.println(idDb);

	System.out.println("========== Mostrando el nombre completo ===========");
	String fullname = repository.getFullNameById(id);
	System.out.println(fullname);

	System.out.println("========== Consulta personalizada por id ===========");
	Object[] personReg = (Object[]) repository.obtenerPersonDataFullById(id);
	System.out.println("id: " + personReg[0] + " name " + personReg[1] + " apellido " + personReg[2] + " lenguaje " + personReg[3]);

	System.out.println("========== Consulta personalizada lista ===========");	
	List<Object[]> regs = repository.obtenerPersonData();
	regs.forEach(reg -> System.out.println("id: " + reg[0] + " name " + reg[1] + " apellido " + reg[2] + " lenguaje " + reg[3]));


	scanner.close();
}

	@Transactional
	public void create() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("nombree");
		String name = scanner.next();
		System.out.println("apellido");
		String lastname = scanner.next();
		System.out.println("lenguaje");
		String language = scanner.next();
		Person person = new Person(null, name, lastname, language);
		scanner.close();
		Person personNew = repository.save(person);
		System.out.println(personNew);
		
		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional
	public void remove(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = scanner.nextLong();

		repository.deleteById(id);
		
		repository.findAll().forEach(System.out::println); 
		scanner.close();
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = scanner.nextLong();

		Optional <Person> personOptional = repository.findById(id);

		personOptional.ifPresentOrElse(repository::delete,
		 () -> System.out.println("El usuario que quiere eliminar no existe"));

		repository.findAll().forEach(System.out::println); 

		scanner.close();
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a editar: ");
		Long idbuscar = scanner.nextLong();
		
		Optional<Person> optionalPerson = repository.findById(idbuscar);
		optionalPerson.ifPresent(person -> {
			System.out.println(person);
			System.out.println("Ingrese el lenguaje a editar");
			String languaje = scanner.next();
			person.setProgrammingLanguage(languaje);
			Person personDb = repository.save(person);
			System.out.println(personDb);
		});
		scanner.close();
	}
	
	@Transactional(readOnly = true)
	public void list() {
		//List<Person> persons = (List<Person>) repository.findAll();
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");
		
		
		persons.stream().forEach(person -> System.out.println(person));
		

		List<Object[]> personValue= repository.obtenerPersonData();
		personValue.stream().forEach(person -> System.out.println(person[0] + " es experto en: " + person[1]));
		
	}
	
	@Transactional(readOnly = true)
	public void findOne() {
	
		// Person person = null; 
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if (optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);
	
		repository.findByNameContaining("se").ifPresent(person -> System.out.println(person));
	
	}
	
}
