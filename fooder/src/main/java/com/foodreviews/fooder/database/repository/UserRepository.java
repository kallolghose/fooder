package com.foodreviews.fooder.database.repository;

import com.foodreviews.fooder.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user u WHERE u.EMAIL = ?1 and u.PASSWORD = ?2", nativeQuery = true)
    User findByEmailAndPassword(String email, String password);
}
