package com.mentor360.dto;

import com.mentor360.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchingResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
