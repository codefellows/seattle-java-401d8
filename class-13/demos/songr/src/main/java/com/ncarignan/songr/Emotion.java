package com.ncarignan.songr;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// 1. Annotations of Entity
// 2. Annotate and add an ID
// 3. create a default constructor
// 4. add jdbc url to application.properties file
// application.properties is like the .env from js - it is public
// 5. Create ClassnameRepository Interface and extend JpaRepository
// 6. Autowire this Repository
// Autowire is a rabbithole
// 7. In application.properties - add Either
// spring.jpa.hibernate.ddl-auto=update (updates every time you restart the app)
// or
// spring.jpa.hibernate.ddl-auto=create (updates only if new Entities are found)

@Entity // this can live in postgres, mysql, mongo, graphql
public class Emotion {

    @Id // id SERIAL PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String color;
    public String feeling;
    boolean causesUsToLaugh;
    String personHavingIt; // Person - TODO: Many to Many
    int level;

    @OneToMany(mappedBy = "emotion", cascade = CascadeType.ALL) // mapped by is my table name
    public List<Expression> expressions = new ArrayList<Expression>();

    public Date whenIFeelIt = new Date(1601400810335l);

    public Emotion(
            String feeling, boolean causesUsToLaugh,
            String personHavingIt, int level,
            String color
    ) {
        this.feeling = feeling;
        this.causesUsToLaugh = causesUsToLaugh;
        this.personHavingIt = personHavingIt;
        this.level = level;
        this.color = color;
    }

    public Emotion(){} // the necessary default constructor

    public boolean isCausesUsToLaugh() {
        return causesUsToLaugh;
    }

    public String getPersonHavingIt() {
        return personHavingIt;
    }

    public int getLevel() {
        return level;
    }

}
