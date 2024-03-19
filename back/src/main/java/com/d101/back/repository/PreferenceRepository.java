package com.d101.back.repository;

import com.d101.back.entity.Preference;
import com.d101.back.entity.composite.UserFoodKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, UserFoodKey> {
}
