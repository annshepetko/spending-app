package com.ann.spending.authorization.service.repository;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.exception.user.UserIsNotRegisteredException;
import com.ann.spending.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryService {

    private final UserRepository userRepository;

    public UserRepositoryService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserIsNotRegisteredException("User is not registered yet"));
    }

    public void save(User user){
        this.userRepository.save(user);
    }

}
