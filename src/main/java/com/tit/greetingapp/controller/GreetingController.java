package com.tit.greetingapp.controller;

import com.tit.greetingapp.model.Greeting;
import com.tit.greetingapp.service.GreetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/service")
    public Greeting getServiceGreeting() {
        return new Greeting(greetingService.getGreetingMessage()); // Uses service layer
    }

    // UC3: Personalized Greeting (New Endpoint)
    @GetMapping("/personalized")
    public Greeting getPersonalizedGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return new Greeting(greetingService.getPersonalizedGreeting(firstName, lastName));
    }

   // UC4 - Save a Greeting Message
   @PostMapping("/save")
   public ResponseEntity<Greeting> saveGreeting(@RequestBody Greeting request) {
       if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
           return ResponseEntity.badRequest().build();
       }
       Greeting savedGreeting = greetingService.saveGreeting(request.getMessage());
       return ResponseEntity.status(HttpStatus.CREATED).body(savedGreeting);
   }


    @GetMapping
    public Greeting getGreeting() {
        return new Greeting("Hello, this is a GET request!");
    }

    @PostMapping
    public Greeting postGreeting() {
        return new Greeting("Hello, this is a POST request!");
    }

    @PutMapping
    public Greeting putGreeting() {
        return new Greeting("Hello, this is a PUT request!");
    }

    @DeleteMapping
    public Greeting deleteGreeting() {
        return new Greeting("Hello, this is a DELETE request!");
    }
}
