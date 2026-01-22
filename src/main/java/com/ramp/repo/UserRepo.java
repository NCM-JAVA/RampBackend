package com.ramp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramp.entity.RoleEntity;
import com.ramp.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    List<Users> findByRole(RoleEntity role);

   
    Optional<Users> findByUserNameIgnoreCaseOrEmailIgnoreCase(
            String userName,
            String email
    );
}
