package com.mirco.curso.springboot.di.factura.springboot_difactura.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.di.factura.springboot_difactura.models.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/invoices")

public class InvoiceController {

    @Autowired
    private Invoice invoice;

    @GetMapping("/show")
    public Invoice show() {
        
        return invoice;
    }
}
