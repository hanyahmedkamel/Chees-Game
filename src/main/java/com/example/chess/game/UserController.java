package com.example.chess.game;

import com.example.chess.game.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Lazy
public class UserController {

    private final JpaUserDetailManager jpaUserDetailsManager;


    public UserController(JpaUserDetailManager jpaUserDetailsManager) {
        this.jpaUserDetailsManager = jpaUserDetailsManager;
    }

    @PostMapping("/SignUp")
    public String SignUP(@RequestBody UserModel requestBody){
        jpaUserDetailsManager.createUser(requestBody);
        return "Success";
    }


    @PostMapping("/changePassword")
    public String changepassword(@RequestBody String newPassword){
        jpaUserDetailsManager.changePassword(newPassword);
        return "Success";
    }







}
