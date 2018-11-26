package com.sebastianfox.food.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastianfox.food.entity.Message;
import com.sebastianfox.food.entity.event.Event;
import com.sebastianfox.food.entity.user.User;
import com.sebastianfox.food.repository.EventRepository;
import com.sebastianfox.food.repository.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/api/event") // This means URL's start with /api (after Application path)
public class EventController {
    private static final String USERNAMENOTAVAILABLE = "Benutzername bereits registriert";
    private static final String USERNOTFOUND = "User in nicht gefunden";
    private static final String EMAILNOTAVAILABLE = "Email Adresse bereits registriert";
    private static final String FACEBOOKUSERCREATED = "Facebook User wurde angelegt";
    private static final String BADCREDENTIALS = "Username oder Passwort falsch";
    private static final String FAILURE = "failure";
    private static final String SUCCESS = "success";

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    @Autowired
    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Event> getAllUserEvents() {
        // This returns a JSON or XML with the users
        return eventRepository.findAll();
    }

    /**
     *
     * @param localeData JSON data from App
     * @return http response
     * @throws JSONException exception
     * @throws IOException exception
     */
    @RequestMapping(path = "/loadAllEvents", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> loadAllEvents(@RequestBody HashMap<String, String> localeData) throws JSONException, IOException {

        Iterable<Event> events = eventRepository.findAll();
        for (Event event : events) {
            System.out.print(event.getTitle());
        }

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,HashMap> data = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();

        // Successful login
        hashMap.put("status","success");
        hashMap.put("events",events);
        data.put("data", hashMap);
        // Object to JSON String
        String jsonString = mapper.writeValueAsString(hashMap);
        // Return to App
        return new ResponseEntity<>(jsonString, HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param eventData JSON data from App
     * @return http response
     * @throws JSONException exception
     * @throws IOException exception
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(path = "/createNewEvent", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> createNewEvent(@RequestBody HashMap<String, String> eventData) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,HashMap> data = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();

        if (userRepository.findByEmail(eventData.get("mail")) == null){
            hashMap.put("status", FAILURE);
            hashMap.put("message", EMAILNOTAVAILABLE);
            data.put("data", hashMap);
            // Object to JSON String
            String jsonString = mapper.writeValueAsString(hashMap);
            return new ResponseEntity<>(jsonString, HttpStatus.CONFLICT);
        }

        User user = userRepository.findByEmail(eventData.get("mail"));
        Event event = new Event();
        event.setTitle(eventData.get("title"));
        event.setDescription(eventData.get("description"));
        event.setOwner(user);
        event.addMember(user);
        eventRepository.save(event);

        // Successful register
        hashMap.put("status","success");
        hashMap.put("event",event);
        data.put("data", hashMap);
        // Object to JSON String
        String jsonString = mapper.writeValueAsString(hashMap);
        // Return to App
        return new ResponseEntity<>(jsonString, HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param eventData JSON data from App
     * @return http response
     * @throws JSONException exception
     * @throws IOException exception
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(path = "/reloadEvent", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> reloadEvent(@RequestBody HashMap<String, Event> eventData) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,HashMap> data = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        Event appEvent = eventData.get("event");

        // check if user is available in database
        if (eventRepository.findById(appEvent.getId()) == null){
            hashMap.put("status", FAILURE);
            hashMap.put("message", USERNOTFOUND);
            //data.put("data", hashMap);
            // Object to JSON String
            String jsonString = mapper.writeValueAsString(hashMap);
            return new ResponseEntity<>(jsonString, HttpStatus.CONFLICT);
        }


        Event event = eventRepository.findById(appEvent.getId());

        // Successful register
        hashMap.put("status","success");
        hashMap.put("event",event);
        //data.put("data", hashMap);
        // Object to JSON String
        String jsonString = mapper.writeValueAsString(hashMap);
        // Return to App
        return new ResponseEntity<>(jsonString, HttpStatus.ACCEPTED);
    }
}
