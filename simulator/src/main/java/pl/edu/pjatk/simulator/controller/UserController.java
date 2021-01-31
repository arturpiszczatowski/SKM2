package pl.edu.pjatk.simulator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<User> {
    UserService userService;

    protected UserController(UserService service) {
        super(service);
        this.userService = service;
    }

    @PostMapping("/{id}/authorities/{authority}")
    public ResponseEntity addAuthority(@PathVariable Long id, @PathVariable String authority) {
            User user = userService.getById(id);
            user.addAuthority(() -> authority);
            return createOrUpdate(user);
    }

    @PutMapping("/{id}/authorities/{authority}")
    public ResponseEntity setAuthority(@PathVariable Long id, @PathVariable String authority) {
            User user = userService.getById(id);
            user.setAuthority(authority);
            return createOrUpdate(user);
    }

    @DeleteMapping("/{id}/authorities/{authority}")
    public ResponseEntity deleteAuthority(@PathVariable Long id, @PathVariable String authority) {
            User user = userService.getById(id);
            user.removeAuthority(() -> authority);
            return createOrUpdate(user);
    }

    @Override
    public Function<User, Map<String, Object>> transformToDTO() {
        return user -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority));

            return payload;
        };
    }

    public ResponseEntity createOrUpdate(User user) {
        try {
            userService.createOrUpdate(user);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
