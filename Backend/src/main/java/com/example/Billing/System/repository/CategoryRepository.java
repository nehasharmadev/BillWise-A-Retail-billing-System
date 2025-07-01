package com.example.Billing.System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Billing.System.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{
	void deleteByCategoryId(String categoryId);
	Optional<CategoryEntity> findByCategoryId(String categoryId);
}
 