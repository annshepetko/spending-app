package com.ann.spending.authorization.service.repository;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.exception.user.UserIsNotRegisteredException;
import com.ann.spending.authorization.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryService {

    private final UserRepository userRepository;

    public UserRepositoryService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    public User findByEmailIfPresent(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserIsNotRegisteredException("User is not registered yet"));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

}
