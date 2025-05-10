package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.exception.UserNotFoundException;
import cz.upce.fei.nnpiacv.security.JwtAuthenticationFilter;
import cz.upce.fei.nnpiacv.exception.EmailAlreadyExistsException;
import cz.upce.fei.nnpiacv.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// WebMvc slice nad Controllerem
@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = {
                // zabijeme defaultní Spring-Security, aby se nekonfigurovaly filtry
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)  // explicitně neaplikovat žádné security filtry
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean      // mockujeme UserService
    private UserService userService;

    @MockitoBean      // mockujeme vlastní JwtAuthenticationFilter -> konstruktor se nespustí
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper json = new ObjectMapper();

    @Test
    @DisplayName("GET /api/v1/users/1 -> 200 OK + správný JSON")
    void getUser_ok() throws Exception {
        // připravíme mock
        User u = new User("test@example.com", "pwd");
        u.setId(1L);
        when(userService.findUserById(1L)).thenReturn(u);

        // proveď request a ověř status + payload
        mockMvc.perform(get("/api/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    /* ---------- GET 404 ---------- */
    @Test @DisplayName("GET /api/v1/users/99 → 404")
    void getUser_notFound() throws Exception {
        when(userService.findUserById(99L)).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")));
    }

    /* ---------- POST 201 ---------- */
    @Test @DisplayName("POST /api/v1/users → 201 Created")
    void postUser_created() throws Exception {
        // heslo má aspoň 6 znaků
        var in  = new User("new@example.com", "password123");
        var out = new User("new@example.com", "password123");
        out.setId(2L);

        when(userService.addUser(any(User.class))).thenReturn(out);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    /* ---------- POST 409 ---------- */
    @Test @DisplayName("POST duplicate email → 409 Conflict")
    void postUser_conflict() throws Exception {
        // opět heslo dost dlouhé
        var in = new User("exists@example.com", "password123");
        when(userService.addUser(any(User.class)))
                .thenThrow(new EmailAlreadyExistsException("exists@example.com"));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(in)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("already exists")));
    }

}
