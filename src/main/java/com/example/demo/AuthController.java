package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>("email занят", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>("успех", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return new ResponseEntity<>("успех", HttpStatus.OK);
        }
        return new ResponseEntity<>("ошибка", HttpStatus.UNAUTHORIZED);
    }
}
