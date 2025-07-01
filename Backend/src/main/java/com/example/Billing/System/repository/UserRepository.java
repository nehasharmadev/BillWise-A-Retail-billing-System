package com.example.Billing.System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Billing.System.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{

	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUserId( String id);
}
