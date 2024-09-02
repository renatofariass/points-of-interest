package com.interest.points.controller;

import com.interest.points.exceptions.UnauthorizedException;
import com.interest.points.services.AuthServices;
import com.interest.points.vos.user.AuthVO;
import com.interest.points.vos.user.RegisterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody @Valid AuthVO data) {
        var token = authServices.signin(data);

        if (token == null) {
            throw new UnauthorizedException("Invalid client request");
        }

        return token;
    }

    @Operation(summary = "Create a user")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid RegisterVO data) {
        return authServices.register(data);
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity signin(@PathVariable("username") String username,
                                 @RequestHeader("Authorization") String refreshToken) {
        if (checkIfParamsIsNotNull(username, refreshToken)) {
            throw new UnauthorizedException("Invalid client request");
        }

        var token = authServices.refreshToken(username, refreshToken);

        if (token == null) throw new UnauthorizedException("Invalid client request");

        return token;
    }

    private static boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }
}