package com.ncarignan.dragon.models.dragon;

import com.ncarignan.dragon.models.user.ApplicationUser;

import javax.persistence.*;

// TODO: needs a repo
@Entity
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    public ApplicationUser applicationUser;

    String color;
    String location;
    int age;

    public Dragon(){};

    public Dragon(String color, String location, int age) {
        this.color = color;
        this.location = location;
        this.age = age;
    }

    public String toString(){
        return String.format(
                "I am a %s dragon from %s and am %d years old",
                color, location, age
        );
    }
}
