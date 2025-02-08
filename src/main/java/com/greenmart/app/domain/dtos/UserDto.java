package com.greenmart.app.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.greenmart.app.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private UUID id;
	private String name;
	private String email;
	private String address;
	private String phoneNumber;
	private LocalDateTime createdAt;
	private String imagePath;
	private UserRole role;
}
