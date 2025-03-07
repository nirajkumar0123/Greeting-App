package com.tit.greetingapp.service;

import com.tit.greetingapp.model.Greeting;
import com.tit.greetingapp.repository.GreetingRepository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public String getGreetingMessage() {
        return "Hello World";
    }

    private final Map<Long, Greeting> greetings = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    // UC3: Personalized Greeting (New Feature)
    public String getPersonalizedGreeting(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            return "Hello, " + firstName + "!";
        } else if (lastName != null) {
            return "Hello, " + lastName + "!";
        } else {
            return getGreetingMessage();  // Reusing UC2 method for default}
        }
    }

    // UC4 - Save a Greeting message
    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }

    // UC5 - Fetch a Greeting message by ID
    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id).orElse(null);
    }

    // UC6 - List all Greeting messages from the repository
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

    // UC7 - Edit a Greeting Message
    public Greeting updateGreeting(Long id, String newMessage) {
        Optional<Greeting> optionalGreeting = greetingRepository.findById(id);
        if (optionalGreeting.isPresent()) {
            Greeting greeting = optionalGreeting.get();
            greeting.setMessage(newMessage);
            return greetingRepository.save(greeting);
        }
        return null; // If greeting with the given ID is not found
    }
    // UC8 - deleting greeting using id
    public boolean deleteGreeting(Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

