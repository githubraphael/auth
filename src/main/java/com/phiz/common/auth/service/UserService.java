package com.phiz.common.auth.service;

import com.phiz.common.core.dto.user.User;

public interface UserService {

    User register(User user);

    User getUserByUserName(String userName);

    User updateUser(String userName, User user);
}
