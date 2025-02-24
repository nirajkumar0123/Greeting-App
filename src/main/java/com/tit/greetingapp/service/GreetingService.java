package com.tit.greetingapp.service;

import com.tit.greetingapp.model.Greeting;
import com.tit.greetingapp.repository.GreetingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}

