package ku.cs.restaurant.controller;

import jakarta.validation.Valid;
import ku.cs.restaurant.dto.LoginRequest;
import ku.cs.restaurant.dto.SignupRequest;
import ku.cs.restaurant.security.JwtUtil;
import ku.cs.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtils;


    @Autowired
    public AuthenticationController(UserService userService,
                                    AuthenticationManager authenticationManager, JwtUtil jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public String authenticateUser(@Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }


    @PostMapping("/signup")
    public String registerUser(@Valid @RequestBody SignupRequest request) {


        if (userService.userExists(request.getUsername()))
            return "Error: Username is already taken!";


        userService.createUser(request);
        return "User registered successfully!";
    }
}


