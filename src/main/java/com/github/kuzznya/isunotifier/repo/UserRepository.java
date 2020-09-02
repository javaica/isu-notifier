package com.github.kuzznya.isunotifier.repo;

import com.github.kuzznya.isunotifier.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
