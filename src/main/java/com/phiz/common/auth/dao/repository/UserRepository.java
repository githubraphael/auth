package com.phiz.common.auth.dao.repository;

import com.phiz.common.auth.dao.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByUserName(String userName);

    UserEntity findByResetToken(String resetToken);

}
