package com.mentor360.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mentor360.auth.dto.LoginRequestDTO;
import com.mentor360.config.JwtUtil;
import com.mentor360.dto.RegisterRequestDTO;
import com.mentor360.exception.InvalidOperationException;
import com.mentor360.exception.ResourceNotFoundException;
import com.mentor360.model.User;
import com.mentor360.repository.UserRepository;
import com.mentor360.service.AuthService;
import com.mentor360.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    // ✅ LOGIN
    @Override
    public String login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidOperationException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // ✅ REGISTER
    @Override
    public String register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new InvalidOperationException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        // 📧 Send email (safe handling)
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Mentor360 Account Verification",
                    "Hi " + user.getName() + ",\n\n" +
                    "Thank you for registering with Mentor360.\n" +
                    "Please verify your email address and update your profile to complete setup.\n\n" +
                    "— Mentor360 Team"
            );
        } catch (Exception e) {
            // ⚠️ Do NOT fail registration if email fails
            System.out.println("Email sending failed: " + e.getMessage());
        }

        return "User registered successfully";
    }
}
