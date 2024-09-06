package com.mirco.curso.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

//    @JoinColumn(name="client_id")
    @OneToMany (cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinTable(
    name="tbl_clientes_to_direcciones",
    joinColumns = @JoinColumn(name="id_cliente"),
    inverseJoinColumns = @JoinColumn(name="id_direcciones"), 
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    private Set<Adress> adresses ;

    
    @OneToMany ( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices; 
    
    public Client() {
        adresses = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Set<Adress> getAdresses() {
        return adresses;
    }

    public void setAdresses(Set<Adress> adresses) {
        this.adresses = adresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setClient(this);
    }
    

    @Override
    public String toString() {
        return "Client {id=" + id + 
        ", name=" + name + 
        ", lastname=" + lastname + 
        ", invoices=" + invoices +
        "}";
    }







    
}
