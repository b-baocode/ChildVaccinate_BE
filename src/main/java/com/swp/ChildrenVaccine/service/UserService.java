package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String generateUserId() {
        Optional<User> lastUser = userRepository.findTopByOrderByIdDesc();
        if (lastUser.isPresent()) {
            String lastId = lastUser.get().getId(); // VD: "U003"
            int number = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("U%03d", number);
        }
        return "U001"; // ID đầu tiên
    }

    public void saveNewUserPassword(User user) {
        userRepository.save(user);
    }
}
