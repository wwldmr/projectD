package com.projectD.multisimbox.dto;

import java.util.List;

public record UserDto(String username, List<String> roles) {}
