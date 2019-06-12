package ch.adisoftware.bookstore.api.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.adisoftware.bookstore.api.exception.UserNotFoundException;
import ch.adisoftware.bookstore.persistence.model.dto.UserDTO;
import ch.adisoftware.bookstore.persistence.model.entity.User;
import ch.adisoftware.bookstore.persistence.repo.UserRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController()
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
            .stream()
            .map(a -> ((GrantedAuthority) a).getAuthority())
            .collect(toList())
        );
        return ok(model);
    }
    
    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO userDTO) {    	
    	if (userDTO.getRoles() == null || userDTO.getRoles().size() == 0) {
    		userDTO.setRoles(Arrays.asList( "ROLE_USER"));
    	}
    	userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    	
        User user = userRepository.save(modelMapper.map(userDTO, User.class));
        return modelMapper.map(user, UserDTO.class);
    }
 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
    	userRepository.deleteById(id);
    }
    
}
