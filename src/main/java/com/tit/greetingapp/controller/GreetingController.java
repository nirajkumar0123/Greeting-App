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


    // UC5 - Fetch Greeting by ID
    @GetMapping("/{id}")
    public com.tit.greetingapp.model.Greeting getGreeting(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }

    //UC6 - get all greeting message
    @GetMapping("/all")
    public List<com.tit.greetingapp.model.Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();
    }

    //UC7- edit greeting message
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGreeting(@PathVariable Long id, @RequestBody Greeting request) {
        com.tit.greetingapp.model.Greeting updatedGreeting = greetingService.updateGreeting(id, request.getMessage());

        if (updatedGreeting != null) {
            return ResponseEntity.ok(updatedGreeting);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Greeting not found!");
        }
    }

    //UC8 - delete greeting by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        return greetingService.deleteGreeting(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
