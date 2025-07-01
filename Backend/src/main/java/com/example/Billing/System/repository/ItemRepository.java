package com.example.Billing.System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Billing.System.entity.CategoryEntity;
import com.example.Billing.System.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>{

	Optional<ItemEntity> findByItemId(String id);
	Integer countByCategoryId(Long id);
	void deleteByItemId(String itemId);
}
