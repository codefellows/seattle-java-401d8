package com.ncarignan.dragon.controllers;

import com.ncarignan.dragon.models.dragon.Dragon;
import com.ncarignan.dragon.models.dragon.DragonRepository;
import com.ncarignan.dragon.models.user.ApplicationUser;
import com.ncarignan.dragon.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DragonController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    DragonRepository dragonRepository;// to interact with db

    @PostMapping("/savedragon")
    public RedirectView makeNewDragon(String color, int age, String location, long id){
        // save a dragon to the db
        Dragon dragon = new Dragon(color, location, age);
        ApplicationUser user = applicationUserRepository.getOne(id);

        dragon.applicationUser = user;

        dragonRepository.save(dragon);
        // show the page of the user that added the dragon

        user.dragons.add(dragon);
        applicationUserRepository.save(user);

        return new RedirectView("/user/" + user.getUsername());
    }

    @PostMapping("/roastMarshmallow")
    public RedirectView roastMarshmallow(String username, long roaster, long roastee){
        Dragon roasterDragon = dragonRepository.findById(roaster).get();
        Dragon roasteeDragon = dragonRepository.findById(roastee).get();

        roasterDragon.dragonsWhoIHaveGivenMarshmallowsTo.add(roasteeDragon);
        roasteeDragon.dragonsWhoHaveGivenMeMarshmallows.add(roasterDragon);

        dragonRepository.save(roasterDragon);
        dragonRepository.save(roasteeDragon);

        return new RedirectView("/user/" + username);
    }
}
