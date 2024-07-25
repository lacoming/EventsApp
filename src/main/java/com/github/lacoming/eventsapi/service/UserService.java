package com.github.lacoming.eventsapi.service;

import com.github.lacoming.eventsapi.entity.Role;
import com.github.lacoming.eventsapi.entity.User;
import com.github.lacoming.eventsapi.exception.EntityNotFoundException;
import com.github.lacoming.eventsapi.exception.RegisterUserException;
import com.github.lacoming.eventsapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new RegisterUserException(
                    MessageFormat.format("User or username {0} or email {1} already exists!",
                            user.getUsername(), user.getEmail())
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(CollectionUtils.isEmpty(user.getRoles())){
            user.addRole(Role.ROLE_USER);

        }

        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("User with id {0} not found!", id)
        ));
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("User with username {0} not found!", username)
        ));
    }

    public Set<String> getEmailsBySubscriptions(Collection<Long> categoriesId, Long organizationId) {
        return userRepository.getEmailsBySubscriptions(categoriesId, organizationId);
    }

}
