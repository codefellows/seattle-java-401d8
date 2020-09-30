package com.ncarignan.songr;

import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class EmotionController {
    @Autowired // connecting to and creating necessary stuff in the db
    public EmotionRepository emotionRepository;

    @Autowired
    public ExpressionRepository expressionRepository;

    @PostMapping("/emotion/delete/{id}")
    public RedirectView removeEmotion(@PathVariable long id){

        emotionRepository.deleteById(id);
        return new RedirectView("/emotions");
    }

    @PostMapping("/emotion") // app.post('/emotion')
    public RedirectView addEmotion(
            String feeling, boolean causesUsToLaugh,
            String personHavingIt, int level,
            String expression, String color
    ){
        Emotion newEmotion = new Emotion(
                feeling,
                causesUsToLaugh,
                personHavingIt,
                level,
                color
        );

        Expression newExpression = new Expression(expression, newEmotion, "The world", 10);
        newEmotion.expressions.add(newExpression);

        emotionRepository.save(newEmotion);
        expressionRepository.save(newExpression);

        return new RedirectView("/emotions");
    }

    @GetMapping("/emotions")
    public String showEmotions(Model m){

        ArrayList<Emotion> emotions = (ArrayList<Emotion>) emotionRepository.findAll();
        m.addAttribute("feelings", emotions);
        return "feelings";
    }


}
