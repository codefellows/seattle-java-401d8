package com.ncarignan.dragon.models.dragon;

import com.ncarignan.dragon.models.user.ApplicationUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// TODO: needs a repo
@Entity
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    public ApplicationUser applicationUser;

    @ManyToMany(cascade = CascadeType.REMOVE)
    // we want this not to delete the other dragon if one gets deleted
    @JoinTable(
            name="dragongift",
            joinColumns = { @JoinColumn(name="giver")},
            inverseJoinColumns = {@JoinColumn(name="receiver")}
    )
    public Set<Dragon> dragonsWhoHaveGivenMeMarshmallows = new HashSet<>();

    @ManyToMany(mappedBy="dragonsWhoHaveGivenMeMarshmallows")
    public Set<Dragon> dragonsWhoIHaveGivenMarshmallowsTo = new HashSet<>();

    public String color;
    public String location;
    public int age;

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

    public long getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public String getColor() {
        return color;
    }

    public String getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }
}
