package com.mentor360.dto;

import lombok.Data;
import com.mentor360.model.enums.Role;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
