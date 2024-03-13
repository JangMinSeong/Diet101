package com.d101.back.repository;

import com.d101.back.entity.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

    @Query("select f from Food f JOIN Preference p ON f.id = p.key.food_id " +
            "JOIN User u ON p.key.user_id = u.id " +
            "WHERE u.email = :email " +
            "ORDER BY p.weight DESC")
    List<Food> rankingByEmail(@Param("email") String email, Pageable pageable);
}
