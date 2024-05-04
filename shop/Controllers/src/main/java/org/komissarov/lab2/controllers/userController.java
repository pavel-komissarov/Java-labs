package org.komissarov.lab2.controllers;

import org.komissarov.shop.app.services.CatOwnerService;
import org.komissarov.shop.app.services.UserService;
import org.komissarov.shop.da.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    UserService userService;

    @Autowired
    CatOwnerService catOwnerService;

    @PostMapping("/reg")
    public void registrationUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userService.addUser(user);
    }

    @PostMapping("/refCatOwner")
    public void ferCatOwner(@RequestParam("id") Long id, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        if (user.getUsername() != null) {
            throw new UsernameNotFoundException("Username not found");
        }

        userService.addCatOwner(user, id);
    }
}
