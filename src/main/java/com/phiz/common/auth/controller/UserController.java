package com.phiz.common.auth.controller;

import com.phiz.common.auth.config.JwtTokenUtil;
import com.phiz.common.auth.model.JwtResponse;
import com.phiz.common.auth.model.Login;
import com.phiz.common.auth.service.UserService;
import com.phiz.common.core.dto.user.User;
import com.phiz.common.core.exception.PhizException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = UserController.USER_BASE_PATH)
public class UserController {

    public static final String USER_BASE_PATH = "/user";
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @ApiOperation("Register user")
    @SneakyThrows
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody final User req) {

        User res = userService.saveUser(req);

        return ResponseEntity.ok(res);
    }

    @ApiOperation("Get user by userName")
    @SneakyThrows
    @GetMapping(path = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable final String userName) {

        User res = userService.getUserByUserName(userName);

        return ResponseEntity.ok(res);
    }

    @ApiOperation("Update user by userName")
    @SneakyThrows
    @PutMapping(path = "/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable final String userName, @RequestBody final User req) {

        User res = userService.updateUser(userName, req);

        return ResponseEntity.ok(res);
    }

    @ApiOperation("Login user")
    @SneakyThrows
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> loginUser(@RequestBody Login login) {

        authenticate(login.getUsername(), login.getPassword());
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(login.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ApiOperation("Change password")
    @SneakyThrows
    @PostMapping(path = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changePassword(@RequestBody final Login login) {

        authenticate(login.getUsername(), login.getPassword());
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(login.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        if (Objects.isNull(token)) {
            new PhizException.Builder("Invalid username/password").withStatus(HttpStatus.BAD_REQUEST).build();
        }

        User req = userService.getUserByUserName(login.getUsername());
        req.setPassword(login.getNewPassword());
        User res = userService.saveUser(req);

        return ResponseEntity.ok(res);
    }

    @ApiOperation("Forgot password")
    @SneakyThrows
    @PostMapping(path = "/forgotPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> forgotPassword(@RequestParam String username) {

        User user = userService.getUserByUserName(username);

        if (Objects.isNull(user)) {
            new PhizException.Builder("Invalid token").withStatus(HttpStatus.BAD_REQUEST).build();
        }

        user.setResetToken(Strings.EMPTY);
        user.setPassword(Strings.EMPTY);
        User res = userService.saveUser(user);


        return ResponseEntity.ok(res);
    }

    @ApiOperation("Forgot password")
    @SneakyThrows
    @PostMapping(path = "/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> resetPassword(@RequestParam String resetToken, @RequestBody Login login) {

        User user = userService.getUserByResetToken(resetToken);

        if (Objects.isNull(user)) {
            new PhizException.Builder("Invalid token").withStatus(HttpStatus.BAD_REQUEST).build();
        }

        user.setResetToken(Strings.EMPTY);
        user.setPassword(login.getNewPassword());
        User res = userService.saveUser(user);

        return ResponseEntity.ok(res);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
