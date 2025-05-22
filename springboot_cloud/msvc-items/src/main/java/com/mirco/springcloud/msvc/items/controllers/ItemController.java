package com.mirco.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.springcloud.msvc.items.models.Item;
import com.mirco.springcloud.msvc.items.models.Product;
import com.mirco.springcloud.msvc.items.services.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService service;
    private final CircuitBreakerFactory cBreakerFactory;

    @Value("${configuracion.text}")
    private String text;

    @Autowired
    private Environment env;


//   @Qualifier("itemServiceWebClient")  // <- esto en el constructor le dice al
// servicio lo que queremos que inyecte

    public ItemController(@Qualifier("itemServiceFeign")ItemService service,
            CircuitBreakerFactory ccBreakerFactory) {
        this.service = service;
        this.cBreakerFactory = ccBreakerFactory;
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("Puerto:", port);
        logger.info(text);
        logger.info(port);

        if (env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
                json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
                json.put("autor.email", env.getProperty("configuracion.autor.email"));

        }

        return ResponseEntity.ok(json);
    }

    @GetMapping
    public List<Item> list(@RequestParam(required = false) String name,
            @RequestHeader(name = "token-request", required = false) String token) {

        logger.info("Llamada a metodo del controller ItemController::list()");
        logger.info("Request parameter: {}", name);
        logger.info("Token: {}", token);
        return service.findAll();
    }

//     @GetMapping("/{id}")
//     public ResponseEntity<?> details(@PathVariable Long id) {
//         Optional<Item> itemOptional = cBreakerFactory.create("items").run(() -> service.findById(id), e -> {
//             System.out.println(e.getMessage());
//             logger.error(e.getMessage());
//             Product product = new Product();
//             product.setCreateAt(LocalDate.now());
//             product.setId(1L);
//             product.setName("Milanesa");
//             product.setPrice(500.00);
//             return Optional.of(new Item(product, 5));
//         });
//         if (itemOptional.isPresent()) {
//             return ResponseEntity.ok(itemOptional.get());
//         }
//         return ResponseEntity.status(404)
//                 .body(Collections.singletonMap(
//                         "message",
//                         "No existe el producto en el microservicio msvc-products"));
//     }

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct")
    @GetMapping("/details/{id}")
    public ResponseEntity<?> details2(@PathVariable Long id) {
            Optional<Item> itemOptional = service.findById(id);

            if (itemOptional.isPresent()) {
                    return ResponseEntity.ok(itemOptional.get());
            }

            return ResponseEntity.status(404)
                            .body(Collections.singletonMap(
                                            "message",
                                            "No existe el producto en el microservicio msvc-products"));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct2")
    @TimeLimiter(name = "items")
    @GetMapping("/details2/{id}")
    public CompletableFuture<?> details3(@PathVariable Long id) {
            return CompletableFuture.supplyAsync(() -> {
                    Optional<Item> itemOptional = service.findById(id);

                    if (itemOptional.isPresent()) {
                            return ResponseEntity.ok(itemOptional.get());
                    }

                    return ResponseEntity.status(404)
                                    .body(Collections.singletonMap(
                                                    "message",
                                                    "No existe el producto en el microservicio msvc-products"));

            });
    }

    public ResponseEntity<?> getFallBackMethodProduct(Throwable e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(
                            LocalDate.now());
            product.setId(1L);
            product.setName("Camara Sony");
            product.setPrice(
                            500.00);
            return ResponseEntity.ok(new Item(product,
                            5));
    }

    public CompletableFuture<?> getFallBackMethodProduct2(Throwable e) {
            return CompletableFuture.supplyAsync(() -> {
                    System.out.println(e.getMessage());
                    logger.error(e.getMessage());

                    Product product = new Product();
                    product.setCreateAt(
                                    LocalDate.now());
                    product.setId(1L);
                    product.setName("CircuitBreaker Open <--------->");
                    product.setPrice(500.00);
                    return ResponseEntity.ok(new Item(product,
                                    5));
            });
    }


    // IMPLEMENTACION ORIGINAL SIN CIRCUIT BRAKER
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
    Optional<Item> itemOptional = service.findById(id);
    if(itemOptional.isPresent()){
    return ResponseEntity.ok(itemOptional.get());
    }
    return ResponseEntity.status(404)
    .body(Collections.singletonMap(
    "message",
    "No existe el producto en el microservicio msvc-products"));
    }


    @PostMapping()
    public ResponseEntity<Product> create(@RequestBody Product product) {
        logger.info("Product creando: {}", product);
        return ResponseEntity.ok(service.save(product));

    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) {
        logger.info("Product actualzado: {}", product);
        return ResponseEntity.ok(service.update(product, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        logger.info("Product id eliminado: {}", id);
        service.delete(id);
    }

}
