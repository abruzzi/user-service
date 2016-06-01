package com.thoughtworks.users.controller;

import com.thoughtworks.users.model.User;
import com.thoughtworks.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> fetchAll() {
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public User update(@PathVariable(value = "id") Long id, @RequestBody User user) {
        User toBeUpdated = userRepository.findOne(id);
        if(toBeUpdated == null) {
            throw new UserNotFoundException("User doesn't exist");
        }

        user.setId(id);
        return userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        User toBeDeleted = userRepository.findOne(id);
        if(toBeDeleted == null) {
            throw new UserNotFoundException("User doesn't exist");
        }
        userRepository.delete(id);
    }

    private class UserNotFoundException extends RuntimeException {
        UserNotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void exceptionHandler() {}
}
