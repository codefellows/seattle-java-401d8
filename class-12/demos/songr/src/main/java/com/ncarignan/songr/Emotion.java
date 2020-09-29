package com.ncarignan.songr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; // we choose this IF we are guessing based on the class' import
import java.sql.Date;

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
    long id;

    public String feeling;
    boolean causesUsToLaugh;
    String personHavingIt;
    int level;
    String reason;
    public Date whenIFeelIt = new Date(1601400810335l);

    public Emotion(String feeling, boolean causesUsToLaugh, String personHavingIt, int level, String reason) {
        this.feeling = feeling;
        this.causesUsToLaugh = causesUsToLaugh;
        this.personHavingIt = personHavingIt;
        this.level = level;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }
}
