package com.capstone1.automatedpayroll.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone1.automatedpayroll.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel,Long>{

    @SuppressWarnings("null")
    @Override
    Optional<UserModel> findById(Long UserId);
    UserModel findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
    
}
