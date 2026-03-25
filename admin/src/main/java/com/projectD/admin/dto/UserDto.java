package com.projectD.admin.dto;

import java.util.List;

public record UserDto(String username, List<String> roles) {
    public UserDto {
        roles = List.copyOf(roles);
    }
}
