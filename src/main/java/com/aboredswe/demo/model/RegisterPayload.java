package com.aboredswe.demo.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterPayload {
    private String name;
    private String email;
    private String password;
}
