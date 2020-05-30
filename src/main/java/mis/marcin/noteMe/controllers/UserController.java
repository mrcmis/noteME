package mis.marcin.noteMe.controllers;

import mis.marcin.noteMe.models.User;
import mis.marcin.noteMe.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller(value = "/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public ResponseEntity<User> addUser(@RequestBody User user){
        User us = (User)userDetailsService.addUser(user);

        return ResponseEntity.ok(us);
    }
}
