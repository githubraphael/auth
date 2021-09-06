package com.phiz.common.auth.utility;

import com.phiz.common.auth.dao.entity.UserEntity;
import com.phiz.common.core.dto.user.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Mapper {

    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    public UserEntity toUserEntity(User user) {

        return modelMapper.map(user, UserEntity.class);
    }

    public User toUserData(UserEntity userEntity) {

        User user = modelMapper.map(userEntity, User.class);
        user.setUserId(userEntity.getId());
        return user;
    }
}
