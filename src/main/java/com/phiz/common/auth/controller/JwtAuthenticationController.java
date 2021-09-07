package com.phiz.common.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
//@RestController
//@CrossOrigin
public class JwtAuthenticationController {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService jwtInMemoryUserDetailsService;
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<JwtResponse> generateAuthenticationToken(@RequestBody Login login)
//            throws Exception {
//
//        authenticate(login.getUsername(), login.getPassword());
//        final UserDetails userDetails = jwtInMemoryUserDetailsService
//                .loadUserByUsername(login.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<JwtResponse> changePassword(@RequestBody Login login)
//            throws Exception {
//
//        authenticate(login.getUsername(), login.getPassword());
//        final UserDetails userDetails = jwtInMemoryUserDetailsService
//                .loadUserByUsername(login.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
}
