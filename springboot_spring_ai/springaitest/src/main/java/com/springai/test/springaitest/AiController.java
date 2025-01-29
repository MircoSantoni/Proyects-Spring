package com.springai.test.springaitest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AiController {

    private final MistralService mistralService;

    public AiController(MistralService mistralService) {
        this.mistralService = mistralService;
    }

    @GetMapping("/generate")
    public Mono<String> generateText(@RequestParam String prompt) {
        return mistralService.generateText(prompt);
    }
}