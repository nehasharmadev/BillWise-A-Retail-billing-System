package com.example.Billing.System.repository;

//import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Billing.System.entity.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long>{

	Optional<OrderEntity> findByOrderId(String orderId);
	
	List<OrderEntity> findAllByOrderByCreatedAtDesc();
	 
	@Query("SELECT SUM(o.grandTotal) FROM OrderEntity o WHERE DATE(o.createdAt)=:date")
	Double sumSalesByDate(@Param("date") LocalDate date);
	
	@Query("SELECT COUNT(o) FROM OrderEntity o WHERE DATE(o.createdAt) = :date")
	Long countByOrderDate(@Param("date") LocalDate date);
	@Query("SELECT o FROM OrderEntity o ORDER By o.createdAt DESC")
	List<OrderEntity>findRecentorders(Pageable pageable);
	
	List<OrderEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
