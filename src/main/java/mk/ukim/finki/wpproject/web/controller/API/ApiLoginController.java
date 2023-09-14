package mk.ukim.finki.wpproject.web.controller.API;

import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpproject.service.AuthService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiLogin")
public class ApiLoginController {
    private final AuthService authService;

    public ApiLoginController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping
    public String login(@RequestBody String requestBody){

        try{
            System.out.println(requestBody);
            JSONObject jsonObject = new JSONObject(requestBody);


            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");

            User user = this.authService.login(username, password);
            return user.getUsername()+"/" +user.getId().toString();
        }
        catch (InvalidUserCredentialsException | JSONException exception) {
            System.out.println("InvalidUserCredentialsException");

        }

        return "1";
    }
}
