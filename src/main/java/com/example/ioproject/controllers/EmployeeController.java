package com.example.ioproject.controllers;

import com.example.ioproject.models.ERole;
import com.example.ioproject.models.Role;
import com.example.ioproject.models.User;
import com.example.ioproject.payload.request.GoogleRequest;
import com.example.ioproject.payload.request.LoginRequest;
import com.example.ioproject.payload.request.SignupRequest;
import com.example.ioproject.payload.response.JwtResponse;
import com.example.ioproject.payload.response.MessageResponse;
import com.example.ioproject.repository.RoleRepository;
import com.example.ioproject.repository.UserRepository;
import com.example.ioproject.security.jwt.JwtUtils;
import com.example.ioproject.security.services.GoogleAuthService;
import com.example.ioproject.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manager")
public class EmployeeController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @PostMapping("/add-worker")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> setUserAsWorker(@RequestParam String email) {
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      return ResponseEntity.badRequest().body(new MessageResponse("Błąd: Użytkownik o podanym e-mailu nie istnieje!"));
    }

    Set<Role> roles = new HashSet<>();

    Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(user);
  }

  @PostMapping("/remove-worker")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> setWorkerAsUser(@RequestParam String email) {
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      return ResponseEntity.badRequest().body(new MessageResponse("Błąd: Użytkownik o podanym e-mailu nie istnieje!"));
    }

    Set<Role> roles = new HashSet<>();

    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(user);
  }
}
