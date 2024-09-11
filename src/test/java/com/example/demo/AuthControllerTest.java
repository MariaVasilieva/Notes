package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginGet() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andReturn();
        String viewName = Objects.requireNonNull(result.getModelAndView()).getViewName();
        assertEquals("login", viewName);
    }

    @Test
    public void testRegisterGet() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andReturn();
        String viewName = Objects.requireNonNull(result.getModelAndView()).getViewName();
        assertEquals("register", viewName);
    }

    @Test
    public void testRegisterSubmitSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andReturn();
    }

    @Test
    public void testRegisterSubmitError() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "user")
                        .param("password", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andReturn();
        String viewName = Objects.requireNonNull(result.getModelAndView()).getViewName();
        assertEquals("register", viewName);
    }

    @Test
    public void testDefaultPathAnonymous() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDefaultPathAuthenticated() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/note/list"))
                .andReturn();
    }
}