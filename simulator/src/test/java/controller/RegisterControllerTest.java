package controller;

import org.apache.catalina.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.controller.RegisterController;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.security.WebSecurityConfig;
import pl.edu.pjatk.simulator.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
@ContextConfiguration(classes = {RegisterController.class, WebSecurityConfig.class})
public class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RegisterController registerController;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;


    @Test
    public void addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"brodo\", \"password\":\"fagins\"}"))
                .andExpect(status().isOk());
        Mockito.verify(userService).createOrUpdate(Mockito.any(User.class));
    }

    @Test
    public void addUserWithExistingUsername() throws Exception {
        Mockito.when(userService.loadUserByUsername("MrRobot")).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"MrRobot\", \"password\":\"ElliotIsntReal\"}"))
                .andExpect(status().isConflict());
    }

}