    package com.mirco.springcloud.msvc.items.services;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Primary;
    import org.springframework.http.MediaType;
    import org.springframework.stereotype.Service;
    import org.springframework.web.reactive.function.client.WebClient;

    import com.mirco.springcloud.msvc.items.entities.Item;

    @Service
    @Primary
    public class ItemServiceWebClient implements ItemService{

        @Autowired
        private  WebClient.Builder webClient;

        @Override
        public List<Item> findAll() {
            return this.webClient.build()
            .get()
            .uri("http://msvc-products")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Item.class)
            .collectList()
            .block();
            }

        @Override
        public Optional<Item> findById(Long id) {
            Map<String, Long> params = new HashMap<>();
            params.put("id", id);
            return Optional.ofNullable(this.webClient.build()
            .get()
            .uri("http://msvc-products/{id}", params)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Item.class)
            .block());
        }

    }
