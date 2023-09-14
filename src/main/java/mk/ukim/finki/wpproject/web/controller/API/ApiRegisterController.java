package mk.ukim.finki.wpproject.web.controller.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.service.AuthService;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiRegister")
public class ApiRegisterController {
    private final UserService userService;

    public ApiRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String registerUser( @RequestBody String requestBody ) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(requestBody, User.class);
            User user1 =  this.userService.register(user.getUsername(), user.getPassword(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail(), user.getPhoneNumber(), Role.ROLE_USER);
            return user1.getUsername()+"/"+user1.getId().toString();
        } catch (JsonProcessingException e) {
            System.out.println("ERORR JsonProcessingException");
        }
        return null;
    }


}
