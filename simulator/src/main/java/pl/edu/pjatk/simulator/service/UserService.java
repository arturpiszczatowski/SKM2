package pl.edu.pjatk.simulator.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.repository.UserRepository;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = repository.findAll().stream()
                .filter(users -> users.getUsername().equals(username))
                .findAny()
                .orElse(null);
        return foundUser;
    }

    @Override
    public User createOrUpdate(User user) {
        if(loadUserByUsername(user.getUsername()) == null){
            GrantedAuthority defaultAuthority = () -> "ROLE_USER";
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);
            user.addAuthority(defaultAuthority);

            return repository.save(user);
        } else {
            var foundUser = loadUserByUsername(user.getUsername());
            String updatedEncodedPassword = passwordEncoder.encode(user.getPassword());

            foundUser.setPassword(updatedEncodedPassword);
            foundUser.setAuthority(user.getAuthority());

            return repository.save(foundUser);
        }
    }


    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
