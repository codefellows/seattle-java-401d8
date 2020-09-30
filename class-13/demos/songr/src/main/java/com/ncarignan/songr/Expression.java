package com.ncarignan.songr;

import javax.persistence.*;

@Entity
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne // many of me to one emotion
    public Emotion emotion;

    public String action;
    public String target;
    public int intensity;

    public Expression(){};

    public Expression(String action, Emotion emotion, String target, int intensity) {
        this.action = action;
        this.emotion = emotion;
        this.target = target;
        this.intensity = intensity;
    }
}
