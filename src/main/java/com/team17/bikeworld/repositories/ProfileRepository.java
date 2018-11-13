package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    String FIND_AVATAR = "SELECT avatarLink FROM profile p where p.account_username = (:username)";

    @Query(value = FIND_AVATAR, nativeQuery = true)
    Object findAvatarByUsername(@Param("username") String uid);
}
