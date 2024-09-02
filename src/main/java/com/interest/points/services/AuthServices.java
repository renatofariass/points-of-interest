package com.interest.points.services;

import com.interest.points.exceptions.BadRequestException;
import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.exceptions.UnauthorizedException;
import com.interest.points.model.User;
import com.interest.points.model.UserRole;
import com.interest.points.repositories.UserRepository;
import com.interest.points.security.jwt.JwtTokenProvider;
import com.interest.points.vos.user.AuthVO;
import com.interest.points.vos.user.RegisterVO;
import com.interest.points.vos.user.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AuthServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<TokenVO> signin(AuthVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);
            var tokenResponse = new TokenVO();
            if(user != null) {
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.getAuthorities());
            }
            else {
                throw new UnauthorizedException("Username not found");
            }
            return ResponseEntity.ok(tokenResponse);
        }
        catch(Exception e) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    public ResponseEntity<Void> register(RegisterVO data) {
        if (userRepository.findByUsername(data.getUsername()) != null) {
            throw new BadRequestException("bad request, you are already a user.");
        }

        String bCryptPassword = passwordEncoder.encode(data.getPassword());

        User user = new User(data.getUsername(), data.getFullName(), bCryptPassword, UserRole.USER);

        User savedUser = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    public ResponseEntity<TokenVO> refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);
        var tokenResponse = new TokenVO();
        if(user != null) {
            tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
        }
        else {
            throw new UnauthorizedException("Username not found");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
