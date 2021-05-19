package com.help.cowin.repo;

import com.help.cowin.pojos.UserEntity;
import com.help.cowin.pojos.UserEntityUV;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityUVRepo extends MongoRepository<UserEntityUV, String> {

    Optional<UserEntityUV> findByEmail(String email);
}
