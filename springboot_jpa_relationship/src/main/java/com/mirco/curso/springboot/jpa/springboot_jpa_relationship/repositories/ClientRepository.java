package com.mirco.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mirco.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client,Long> {

    @Query("select c from Client as c left Join fetch c.adresses where c.id =?1")
    Optional<Client> findOneWithAddresses(Long id);

    @Query("select c from Client as c left Join fetch c.invoices where c.id =?1")
    Optional<Client> findOneWithInvoices(Long id);

    @Query("select c from Client as c left Join fetch c.invoices left Join fetch c.adresses where c.id =?1")
    Optional<Client> findOne(Long id);

}
