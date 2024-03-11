package com.d101.back.repository;

import com.d101.back.entity.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

    @Query("select f from Food f " +
            "where f.id = (select i.key.food_id from Intake i " +
            "where i.key.meal_id = (select m.id from Meal m where m.user.id = (select u.id from User u where u.email = :email) ) " +
            "group by i.key.meal_id " +
            "order by count(i.key.food_id) desc)")
    List<Food> rankingByEmail(@Param("email") String email, Pageable pageable);
}
