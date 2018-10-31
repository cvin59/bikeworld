package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
