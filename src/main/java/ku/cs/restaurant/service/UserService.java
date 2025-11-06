package ku.cs.restaurant.service;


import jakarta.persistence.EntityExistsException;
import ku.cs.restaurant.dto.SignupRequest;
import ku.cs.restaurant.entities.User;
import ku.cs.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;


@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder encoder) {


        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }


    public void createUser(SignupRequest request) {
        if (userExists(request.getUsername()))
            throw new EntityExistsException("User with the username already exists.");
        User dao = new User();
        dao.setUsername(request.getUsername());
        dao.setPassword(encoder.encode(request.getPassword()));
        dao.setName(request.getName());
        dao.setRole("ROLE_USER");
        dao.setCreatedAt(Instant.now());


        userRepository.save(dao);
    }
}
