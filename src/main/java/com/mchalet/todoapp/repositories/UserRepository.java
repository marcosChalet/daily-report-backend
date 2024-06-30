package com.mchalet.todoapp.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.mchalet.todoapp.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);    
}
