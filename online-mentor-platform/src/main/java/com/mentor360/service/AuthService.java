package com.mentor360.service;

import com.mentor360.auth.dto.LoginRequestDTO;
import com.mentor360.dto.RegisterRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO request);

    String register(RegisterRequestDTO request);
}
