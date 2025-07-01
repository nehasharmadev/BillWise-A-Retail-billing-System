package com.example.Billing.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Billing.System.entity.OrderItemEntity;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long>{

}
