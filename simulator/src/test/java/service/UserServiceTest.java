package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.repository.UserRepository;
import pl.edu.pjatk.simulator.service.UserService;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {UserService.class})
public class UserServiceTest {

    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setup() {
        this.userService = Mockito.spy(new UserService(userRepository));
    }

    @Test
    public void getAllUsers() {
        userService.getAll();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void getUserByID() {
        Long id = 1L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.getById(id);
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    public void addUser() {
        User user = new User("Sukaru", "Horanu", "ROLE_ADMIN");
        userService.createOrUpdate(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void modifyUser() {
        User user = Mockito.spy(new User("Garen", "TheJarvan", "ROLE_PRIVILEGED"));
        user.setId(1L);

        Optional<User> optionalUser = Optional.of(new User("Garen", "TheJarvan", "ROLE_PRIVILEGED"));

        Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);

        userService.createOrUpdate(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void deleteUserByID() {
        User user = new User("Bobobo-bo", "Bo-bobo", "ROLE_ADMIN");
        Optional<User> userOptional = Optional.of(user);

        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        userService.delete(1L);

        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).delete(userOptional.get());
    }

}