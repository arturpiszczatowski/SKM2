package pl.edu.pjatk.simulator.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails, DbEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String authority;

    public User() {}

    public User(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays
                .stream(this.authority.split(","))
                .map(String::trim)
                .filter(authority -> !authority.equals(""))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public void addAuthority(GrantedAuthority authority) {
        String newAuthority = authority.getAuthority().trim();
        String currentAuthorities = this.authority == null ? "" : (this.authority + ",");
        this.authority = currentAuthorities + newAuthority;
    }

    public void removeAuthority(GrantedAuthority authority) {
        String deletedAuthority = authority.getAuthority().trim();
        String remainingAuthorities = this.authority.replace(deletedAuthority, "")
                .replace(",,", "")
                .trim();
        this.authority = remainingAuthorities;
    }

}
