package model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import pl.edu.pjatk.simulator.model.User;

import java.util.List;
import java.util.stream.Collectors;


public class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User("Norotu", "Azamuki", "ROLE_USER");
    }


    @Test
    void getUsernameTest() {
        Assert.assertEquals("Norotu", user.getUsername());
    }

    @Test
    void setUsernameTest() {
        user.setUsername("Guko");
        Assert.assertEquals("Guko", user.getUsername());
    }


    @Test
    void addAuthority() {
        user.addAuthority(() -> "ROLE_ADMIN");
        user.addAuthority(() -> "ROLE_PRIVILEGED");
        Assert.assertEquals(3, user.getAuthorities().size());

        var authorities = getAuthoritiesInTest(user);
        Assert.assertTrue(authorities.contains("ROLE_USER"));
        Assert.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assert.assertTrue(authorities.contains("ROLE_PRIVILEGED"));
    }

    @Test
    void removeAuthority() {
        user.removeAuthority(() -> "ROLE_USER");
        Assert.assertEquals(0, user.getAuthorities().size());

        var authorities = getAuthoritiesInTest(user);
        Assert.assertEquals(0, authorities.size());
    }

    @Test
    void setAuthorities() {
        user.setAuthority( "ROLE_ADMIN");
        Assert.assertEquals(1, user.getAuthorities().size());

        var authorities = getAuthoritiesInTest(user);
        Assert.assertFalse(authorities.contains("ROLE_USER"));
        Assert.assertEquals(1, authorities.size());

        user.setAuthority( "ROLE_ADMIN,ROLE_USER");
        Assert.assertEquals(2, user.getAuthorities().size());
        Assert.assertTrue(getAuthoritiesInTest(user).contains("ROLE_USER"));
        Assert.assertTrue(getAuthoritiesInTest(user).contains("ROLE_ADMIN"));
    }

    List<String> getAuthoritiesInTest(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}