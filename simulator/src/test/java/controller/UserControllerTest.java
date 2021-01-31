package controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.controller.UserController;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.security.WebSecurityConfig;
import pl.edu.pjatk.simulator.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = {"ROLE_ADMIN"})
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, WebSecurityConfig.class})
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(userService).getAll();
    }

    @Test
    public void getUserByID() throws Exception {
        User user = Mockito.spy(new User("Sesaku", "Achuchi", "ROLE_PRIVILEGED"));

        Mockito.when(userService.getById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk());

        Mockito.verify(userService).getById(1L);
    }

    @Test
    public void getUserByIDNotFound() throws Exception {
        Mockito.when(userService.getById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isInternalServerError());

        Mockito.verify(userService).getById(1L);
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void deleteUserByIdForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUserByIdAccepted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Mockito.verify(userService).delete(1L);
    }

    @Test
    public void modifyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"13\", \"username\":\"Bohny\", \"password\":\"Jravo\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(userService).createOrUpdate(Mockito.any(User.class));
    }

    @Test
    public void addAuthorityTest() throws Exception {
        User user = Mockito.spy(new User("super", "man", ""));

        Mockito.when(userService.getById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/authorities/ROLE_USER").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Mockito.verify(user).addAuthority(Mockito.any(GrantedAuthority.class));
        Mockito.verify(userService).createOrUpdate(Mockito.any(User.class));

        Assert.assertTrue(user.getAuthority().contains("ROLE_USER"));
    }

    @Test
    public void setAuthorityTest() throws Exception {
        User user = Mockito.spy(new User("spider", "man", "ROLE_PRIVILEGED"));

        Mockito.when(userService.getById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1/authorities/ROLE_USER").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Mockito.verify(user).setAuthority("ROLE_USER");
        Mockito.verify(userService).createOrUpdate(Mockito.any(User.class));

        Assert.assertEquals("ROLE_USER", user.getAuthority());
        Assert.assertFalse(user.getAuthority().contains("ROLE_PRIVILEGED"));
    }

    @Test
    public void deleteAuthorityTest() throws Exception {
        User user = Mockito.spy(new User("bunny", "theBucks", "ROLE_USER"));

        Mockito.when(userService.getById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/authorities/ROLE_USER").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Mockito.verify(user).removeAuthority(Mockito.any(GrantedAuthority.class));
        Mockito.verify(userService).createOrUpdate(Mockito.any(User.class));

        Assert.assertEquals("", user.getAuthority());
    }
}