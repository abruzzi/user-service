package com.thoughtworks.users.service;

import com.thoughtworks.users.model.LadderUserDetails;
import com.thoughtworks.users.model.User;
import com.thoughtworks.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LadderUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        if(user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new LadderUserDetails(user);
    }
}
