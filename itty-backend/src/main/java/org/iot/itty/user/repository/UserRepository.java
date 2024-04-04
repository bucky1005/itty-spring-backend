package org.iot.itty.user.repository;

import org.iot.itty.user.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	UserEntity findUserCodePkByUserEmail(String userEmail);
	// Boolean existsBy(String userEmail);
}
