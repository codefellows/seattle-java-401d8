package com.ncarignan.songr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class EmotionController {
    @Autowired // connecting to and creating necessary stuff in the db
    public EmotionRepository emotionRepository;

    @PostMapping("/emotion") // app.post('/emotion')
    public RedirectView addEmotion(String feeling, boolean causesUsToLaugh, String personHavingIt, int level, String reason){
        Emotion newEmotion = new Emotion(
                feeling,
                causesUsToLaugh,
                personHavingIt,
                level,
                reason
        );

        emotionRepository.save(newEmotion);

        return new RedirectView("/emotions");
    }

    @GetMapping("/emotions")
    public String showEmotions(Model m){

        ArrayList<Emotion> emotions = (ArrayList<Emotion>) emotionRepository.findAll();

        m.addAttribute("feelings", emotions);
        return "feelings";
    }


}
