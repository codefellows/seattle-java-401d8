package com.ncarignan.songr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // this annotation tells spring to look at this class and use its methods to establish routes
public class HomeController {
    @GetMapping("/") // in js: app.get("/")
    public String showHome(){ // all mapping methods that show webpages must be public and must return strings
        return "home"; //  a filename for the thymeleaf file we want to render (html file)
        // think ejs, we returned "home.ejs" if we wanted to render that file
    }

    // same as in express app.get('/greeting/:to')
    @GetMapping("/greeting/{to}") // localhost:8080/greeting?person="Mike"&lastSeen=0&message="good to see you"
    public String sayHello(
            Model mPotato,
            String person,
            Integer lastSeen,
            String message,
            @PathVariable String to // matches to the path variable of {to}
            ){ // get mapping's parameters assume Query String params
        System.out.println("To : " + to);
        System.out.println(String.format("Message from %s : its been %d days since I saw you, %s", person, lastSeen, message));

        mPotato.addAttribute("person", person);
        mPotato.addAttribute("daysSinceISawYou", lastSeen);
        mPotato.addAttribute("message", message);

        return "hello"; // this is connected to the file hello.html
        // ejs: res.render("hello", {person: person, message : message, lastSeen : lastSeen});
    }
}
