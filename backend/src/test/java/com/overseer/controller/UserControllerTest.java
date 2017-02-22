package com.overseer.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.overseer.Application;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void getAllUser() throws Exception {
        User user = new User("Andrey","Sidorov","12345","andrey@mail.com", Role.EMPLOYEE);
        User user2 = new User("Artemiy","Sidorov","1re45","anwdey@mail.com", Role.ADMINISTRATOR);
        User user3 = new User("Mao","Zedung","knr","zedung@mail.com", Role.MANAGER);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService userService = mock(UserService.class);
        when(userService.findAll()).thenReturn(users);
        UserController userController = new UserController(userService);
        System.out.println(userController.getAllUser());
    }

    @Test
    public void getUser() throws Exception {
        String expected = "<200 OK,User(firstName=Andrey, lastName=Sidorov, secondName=null, password=121da, email=andrey@mail.com, dateOfBirth=null, phoneNumber=null, role=employee),{}>";
        User user = new User("Andrey","Sidorov","121da","andrey@mail.com", Role.EMPLOYEE);
        user.setId(1l);
        UserService userService = mock(UserService.class);
        when(userService.findOne(1l)).thenReturn(user);
        UserController userController = new UserController(userService);
        assertEquals(expected,userController.getUser("1").toString());
    }

    @Test
    public void addUser() throws Exception {
        User unsaved = new User("Andrey","Sidorov","121da","andrey@mail.com", Role.EMPLOYEE);
        User saved = new User("Andrey","Sidorov","121da","andrey@mail.com", Role.EMPLOYEE);
        saved.setId(2053l);
        UserService userService = mock(UserService.class);
        when(userService.create(unsaved)).thenReturn(saved);
        UserController controller = new UserController(userService);
        mockMvc = standaloneSetup(controller).build();
        //MAPPER
        System.out.println(MAPPER.writeValueAsString(unsaved));
        mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(unsaved))).andExpect(status().isCreated());
        verify(userService, atLeastOnce()).create(unsaved);
    }


}