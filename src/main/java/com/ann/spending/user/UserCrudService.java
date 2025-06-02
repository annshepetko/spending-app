package com.ann.spending.user;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.user.profile.dto.UserView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCrudService {

    private final UserRepository userRepository;

    public UserCrudService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void updateUser(UserView userView, User user) {
        user.setName(userView.username());
        user.setPhoneNumber(userView.phoneNumber());

        userRepository.save(user);
    }
}
