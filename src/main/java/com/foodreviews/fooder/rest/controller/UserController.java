package com.foodreviews.fooder.rest.controller;

import com.foodreviews.fooder.database.models.User;
import com.foodreviews.fooder.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public @ResponseBody Iterable<User> getAllUsers(){
        logger.info("Get All Users");
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        logger.info("Get the user by ID : " + id);
        Optional<User> userG = userRepository.findById(id);
        return userG.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    public ResponseEntity<User> newUser(@Valid @RequestBody User user) throws Exception {
        logger.info("Create new User");
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(Objects.isNull(existingUser)) {
            User result = userRepository.save(user);
            return ResponseEntity.created(new URI("/api/user" + result.getId())).body(result);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

}
