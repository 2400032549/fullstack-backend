package com.mentor360.dto;

import com.mentor360.model.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
	@NotBlank
    private String name;
	@Email
    private String email;
	@Size(min = 6)
    private String password;
	@NotNull
    private Role role; // USER / ADMIN
}
