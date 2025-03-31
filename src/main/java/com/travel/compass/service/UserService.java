//package com.travel.compass.service;
//
//
//import com.travel.compass.model.User;
//import com.travel.compass.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public User registerUser(String firstName, String lastName, String email, String password) {
//
//        email = email.toLowerCase().trim(); // Convert email to lowercase and remove spaces
//
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new RuntimeException("Email already exists!");
//        }
//
//        User user = new User();
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//
//        return userRepository.save(user);
//    }
//
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//
//}


package com.travel.compass.service;


import com.travel.compass.model.User;
import com.travel.compass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String firstName, String lastName, String email, String password) {

        email = email.toLowerCase().trim(); // Convert email to lowercase and remove spaces

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        user.setRole("USER"); // Assign default role
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User updateUser(Long userId, String firstName, String lastName, String email, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Partial updates
        if (firstName != null && !firstName.isEmpty()) user.setFirstName(firstName);
        if (lastName != null && !lastName.isEmpty()) user.setLastName(lastName);

        if (email != null && !email.isEmpty()) {
            email = email.toLowerCase().trim();
            if (!email.equals(user.getEmail()) && userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Email already taken!");
            }
            user.setEmail(email);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
