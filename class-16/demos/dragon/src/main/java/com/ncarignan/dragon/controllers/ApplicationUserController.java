package com.ncarignan.dragon.controllers;

import com.ncarignan.dragon.models.user.ApplicationUser;
import com.ncarignan.dragon.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/newuser")
    public RedirectView makeNewUser(String username, String passwordsilly){
        System.out.println("adding a user");
        passwordsilly = passwordEncoder.encode(passwordsilly);

        // make user
        ApplicationUser newUser = new ApplicationUser(username, passwordsilly);

        //save user
        applicationUserRepository.save(newUser);

        //send them home
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String login (){
        return "login";
    }
}
