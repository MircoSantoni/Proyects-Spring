package com.mirco.curso.springboot.di.factura.springboot_difactura;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.mirco.curso.springboot.di.factura.springboot_difactura.models.Item;
import com.mirco.curso.springboot.di.factura.springboot_difactura.models.Product;

@Configuration
@PropertySource("classpath:data.properties")
public class AppConfig {

    @Bean
    List<Item> itemsInvoice() {
        Product p1 = new Product("Camara de fotos", 1234);
        Product p2 = new Product("bicicleta robada", 233);
        return Arrays.asList(new Item(p1,2), new Item(p2, 3));

    }

    @Bean("default")
    //@Primary
    List<Item> itemsInvoiceOffice() {
        Product p1 = new Product(" La tanga del ladyboy ", 777);
        Product p2 = new Product(" Agua de coco", 123);
        Product p3 = new Product("Billetera alemana", 200);
        Product p4 = new Product("Celular samnsung re pateado", 800);
        return Arrays.asList(new Item(p1,2), new Item(p2, 3), new Item(p3, 1), new Item(p4, 5));

    }
}
