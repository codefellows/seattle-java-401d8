package com.ncarignan.songr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ExpressionController {

    @Autowired
    EmotionRepository emotionRepository;

    @Autowired
    ExpressionRepository expressionRepository;

    @PostMapping("/expression")
    public RedirectView addExpression(String action, long emotionId){
        Emotion em = emotionRepository.getOne(emotionId);
        Expression expr = new Expression(action, em, "Nicholas", 10);
        expressionRepository.save(expr);

        return new RedirectView("/emotions");
    }
}
