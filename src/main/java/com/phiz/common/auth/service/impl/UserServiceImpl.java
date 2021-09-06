package com.phiz.common.auth.service.impl;

import com.phiz.common.auth.dao.entity.UserEntity;
import com.phiz.common.auth.dao.repository.UserRepository;
import com.phiz.common.auth.service.UserService;
import com.phiz.common.auth.utility.Mapper;
import com.phiz.common.core.dto.user.User;
import com.phiz.common.core.exception.PhizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User userData) {

        User user = new User();
        try {
            userData.setPassword(passwordEncoder.encode(userData.getPassword()));
            UserEntity userEntity = userRepository.save(mapper.toUserEntity(userData));
            user = mapper.toUserData(userEntity);
        } catch (DuplicateKeyException e) {
            new PhizException.Builder(e.getMessage()).withStatus(HttpStatus.BAD_REQUEST).build();
        }

        return user;
    }

    @Override
    public User getUserByUserName(String userName) {

        UserEntity userEntity = userRepository.findByUserName(userName);

        if (Objects.isNull(userEntity)) {
            throw new PhizException.Builder(userName + " not found").withStatus(HttpStatus.NOT_FOUND).build();
        }
        return mapper.toUserData(userEntity);
    }

    @Override
    public User updateUser(String userName, User user) {

        UserEntity tmpEntity = userRepository.findByUserName(userName);

        if (Objects.isNull(tmpEntity)) {
            throw new PhizException.Builder(userName + " not found").withStatus(HttpStatus.NOT_FOUND).build();
        }

        UserEntity userEntity = mapper.toUserEntity(user);
        userEntity.setId(tmpEntity.getId());

        userRepository.save(userEntity);

        return mapper.toUserData(userEntity);
    }
}
