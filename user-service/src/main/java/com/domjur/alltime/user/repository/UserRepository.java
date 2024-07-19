package com.domjur.alltime.user.repository;

import com.domjur.alltime.user.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository  extends JpaRepository<UserResponse, String> {
}
