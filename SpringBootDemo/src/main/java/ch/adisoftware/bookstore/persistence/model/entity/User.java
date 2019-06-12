package ch.adisoftware.bookstore.persistence.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String firstname;
    
    private String lastname;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    
    public User() {
    	super();
    }
    
    public User(String username, String password, List<String> roles) {
    	this();
    	
    	this.username = username;
    	this.password = password;
    	this.roles = roles;
    }
    
    public User(String firstname, String lastname, String username, String password, List<String> roles) {
    	this(username, password, roles);
    	
    	this.firstname = firstname;
    	this.lastname = lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
    	this.firstname = firstname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
    	this.lastname = lastname;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public List<String> getRoles() {
    	return roles;
    }
    
    public void setRoles(List<String> roles) {
    	this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String toString() {
    	return this.username + " " + this.password;
    }
}
