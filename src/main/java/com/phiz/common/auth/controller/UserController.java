package com.phiz.common.auth.controller;

import com.phiz.common.auth.service.UserService;
import com.phiz.common.core.dto.user.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = UserController.USER_BASE_PATH)
public class UserController {

    public static final String USER_BASE_PATH = "/user";
    private final UserService userService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Register user")
    @SneakyThrows
    public ResponseEntity<User> register(@RequestBody final User req) {

        User res = userService.register(req);

        return ResponseEntity.ok(res);
    }

    @GetMapping(path = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get user by userName")
    @SneakyThrows
    public ResponseEntity<User> getUser(@PathVariable final String userName) {

        User res = userService.getUserByUserName(userName);

        return ResponseEntity.ok(res);
    }

    @PutMapping(path = "/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get user by userName")
    @SneakyThrows
    public ResponseEntity<User> updateUser(@PathVariable final String userName, @RequestBody final User req) {

        User res = userService.updateUser(userName, req);

        return ResponseEntity.ok(res);
    }
}
