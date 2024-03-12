package com.d101.back.repository;

import com.d101.back.entity.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

    @Query("select f from Food f where f.id in (select p.key.food_id from Preference p, User u where (u.email = :email and p.key.user_id = u.id) order by p.weight desc)")
    List<Food> rankingByEmail(@Param("email") String email, Pageable pageable);
}
