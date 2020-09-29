package com.ncarignan.songr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class EmotionController {
    @GetMapping("/emotions")
    public String showEmotions(Model m){
        ArrayList<Emotion> emotions = new ArrayList<>();
        emotions.add(new Emotion(
                "chill",
                true,
                "Jack",
                2,
                "Cause I am learning"
        ));
        emotions.add(new Emotion(
                "Happy",
                false,
                "Matthew",
                9,
                "Wife just got home"
        ));
        emotions.add(new Emotion(
                "Satisfied",
                false,
                "Paul",
                7,
                "Not talking on phone anymore"
        ));

        emotions.add(new Emotion(
                "Challenged",
                false,
                "Meghan",
                7,
                "Learning something new"
        ));

            m.addAttribute("feelings", emotions);
            return "feelings";
    }
}
